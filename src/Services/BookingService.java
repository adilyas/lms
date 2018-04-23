package Services;

import DAO.CopyDAO;
import Database.Database;
import Objects.*;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

public class BookingService {
    static private Database database;
    static private LoggingService loggingService;
    static private NotifyService notifyService;

    public static void init(LoggingService loggingService, Database database, NotifyService notifyService) {
        BookingService.database = database;
        BookingService.loggingService = loggingService;
        BookingService.notifyService = notifyService;
    }

    static void setDatabase(Database database) {
        BookingService.database = database;
    }

    public static void setLoggingService(LoggingService loggingService) {
        BookingService.loggingService = loggingService;
    }

    public static void setNotifyService(NotifyService notifyService) {
        BookingService.notifyService = notifyService;
    }

    /**
     * Implements document booking logic.
     * Depending on user type sets the priority of the user in the order.
     *
     * @param patron   booking patron
     * @param document booking document
     * @throws SQLException
     */
    public static void book(Patron patron, Document document) throws SQLException {
        String insertText = "INSERT INTO lms.patron_booked_document " +
                "(person_id, document_id, priority, date) VALUES (?, ?, ?, ?);";
        PreparedStatement st = database.getConnection().prepareStatement(insertText);

//        less priority value means more important the user is
        int priority;
        switch (patron.getType()) {
            case "student":
                priority = 1;
                break;
            case "instructor":
                priority = 2;
                break;
            case "TA":
                priority = 3;
                break;
            case "VP":
                priority = 4;
                break;
            case "professor":
                priority = 5;
                break;
            default:
                throw new NoSuchElementException("Wrong type; " +
                        "Also BookingService.book doesn't support librarian types");
        }

        LocalDate currentDate = LocalDate.now();

        st.setInt(1, patron.getId());
        st.setInt(2, document.getId());
        st.setInt(3, priority);
        st.setDate(4, Date.valueOf(currentDate));
        st.executeUpdate();
        database.getConnection().commit();
    }

    /**
     * Implements checking out logic.
     * Depending on user type and document type sets dueDate.
     *
     * @param patron    checking out patron
     * @param librarian librarian who has
     *                  let patron to check out the document
     * @param document  particular document the patron is checking out
     * @throws SQLException
     */
    public static void checkOut(Patron patron, Librarian librarian, Document document) throws SQLException {
        loggingService.logString("CHECKOUT START\n" +
                "BY " + patron + "\n" +
                "VERIFIED BY " + librarian + "\n" +
                "DOCUMENT " + document + "\n");
        Copy copy = document.getFreeCopy();
        if (copy == null) {
            loggingService.logString("CHECKOUT FINISH\n" +
                    "RESULT " + "No free copy available.");
            throw new NoSuchElementException("No free copy available.");
        }
        int dueWeeks = calcDueWeeks(patron, copy);
        LocalDate currentDate = LocalDate.now();
        LocalDate dueDate = currentDate.plusWeeks(dueWeeks);

        copy.setCheckedOut(true);
        copy.setHolder(patron);
        copy.setCheckedOutDate(LocalDate.now());
        copy.setDueDate(LocalDate.now().plusWeeks(calcDueWeeks(patron, copy)));

        CopyDAO.update(copy);
        loggingService.logString("CHECKOUT FINISH\n" +
                "RESULT " + "Checked out on " + copy.getCheckedOutDate() + " until " + copy.getDueDate() + ".");
    }

    /**
     * Renews patron checked out copy relation considering
     * required conditions. Updates appropriate fields in the database.
     *
     * @param patron patron who wants to renew
     * @param copy   copy which is going to be renewed
     * @throws SQLException
     */
    public static void renew(Patron patron, Copy copy) throws SQLException {
        if (copy.getRenewTimes() > 1 && !patron.getType().equals("VP")) {
            return; // may be it's better to write some feedback here
        }

        String updateText = "UPDATE copies SET renew_times = renew_times + 1, check_out_date = ?, due_date = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(updateText);

        int dueWeeks = calcDueWeeks(patron, copy);
        LocalDate newCheckOutDate = copy.getDueDate();
        LocalDate newDueDate = newCheckOutDate.plusWeeks(dueWeeks);

        st.setDate(1, Date.valueOf(newCheckOutDate));
        st.setDate(2, Date.valueOf(newDueDate));
        st.executeUpdate();
        database.getConnection().commit();
    }

    private static int calcDueWeeks(Patron patron, Copy copy) {
        int dueWeeks = 3;
        String docType = copy.getDocument().getType();
        if (patron.getType().equals("VP")) {
            dueWeeks = 1;
        } else if (patron.getType().equals("instructor") ||
                patron.getType().equals("professor") ||
                patron.getType().equals("TA")) {
            dueWeeks = 4;
        } else if (docType.equals("book") &&
                ((Book) copy.getDocument()).getBestseller()) {
            dueWeeks = 2;
        } else if (docType.equals("av_material") ||
                docType.equals("journal_issue")) {
            dueWeeks = 2;
        }

        return dueWeeks;
    }

    public static void outstandingRequest(Librarian librarian, Document document) throws SQLException {
        loggingService.logString("OUTSTANDING REQUEST START\n" +
                "LIBRARIAN " + librarian + "\n" +
                "DOCUMENT " + document + "\n");

        notifyService.notify(document.getBookedBy(), "Sorry your request for " + document.getTitle() +
                " was declined because of outstanding request. Can try to book this book again or ask librarian.");

        notifyService.notify(document.getCopies().stream()
                .map(Copy::getHolder)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()), "You should return " + document.getTitle() +
                " due to outstanding request. Sorry, ask librarian for more details.");

        String query = "DELETE FROM patron_booked_document WHERE document_id = ?;";
        PreparedStatement statement = database.getConnection().prepareStatement(query);
        statement.setInt(1, document.getId());
        statement.executeUpdate();
        database.getConnection().commit();
    }
}
