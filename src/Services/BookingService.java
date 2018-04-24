package Services;

import DAO.CopyDAO;
import Database.Database;
import Objects.*;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

public class BookingService {
    private Database database;
    private LoggingService loggingService;
    private NotifyService notifyService;

    public BookingService(Database database, LoggingService loggingService, NotifyService notifyService) {
        this.database = database;
        this.loggingService = loggingService;
        this.notifyService = notifyService;
    }

    void setDatabase(Database database) {
        this.database = database;
    }

    public void setLoggingService(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    public void setNotifyService(NotifyService notifyService) {
        this.notifyService = notifyService;
    }


    /**
     * Implements document booking logic.
     * Depending on user type sets the priority of the user in the order.
     *
     * @param patron   booking patron
     * @param document booking document
     * @throws SQLException
     */
    public void book(Patron patron, Document document) throws SQLException {
        loggingService.logString("BOOK START\n" +
                "BY " + patron + "\n" +
                "DOCUMENT " + document);
        String query = "INSERT INTO patron_booked_document " +
                "(person_id, document_id, priority, request_date) VALUES (?, ?, ?, ?);";
        PreparedStatement statement = database.getConnection().prepareStatement(query);
        statement.setInt(1, patron.getId());
        statement.setInt(2, document.getId());
        statement.setDate(4, Date.valueOf(LocalDate.now()));

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
                loggingService.logString("BOOK FINISH\n" +
                        "RESULT " + "User of type " + patron.getType() + " can't book documents or " +
                        "should not exist at all."
                );
                throw new NoSuchElementException("Wrong type of patron.");
        }

        statement.setInt(3, priority);
        statement.executeUpdate();
        database.getConnection().commit();

        document.getBookedBy().add(patron);
        patron.getWaitingList().add(document);

        if (document.getFreeCopy() != null) {
            notifyService.notifyAboutFreeCopy(patron, document);
            query = "UPDATE patron_booked_document SET " +
                    "notification_date = ? WHERE person_id = ? AND document_id = ?;";
            statement = database.getConnection().prepareStatement(query);
            statement.setDate(1, Date.valueOf(LocalDate.now()));
            statement.setInt(2, patron.getId());
            statement.setInt(3, document.getId());
            statement.executeUpdate();
            database.getConnection().commit();
            loggingService.logString("BOOK FINISH\n" +
                    "RESULT " + "User " + patron + " now in queue for document " + document + " and notified about free copy.");
        } else {
            loggingService.logString("BOOK FINISH\n" +
                    "RESULT " + "User " + patron + " now in queue for document " + document + ".");
        }
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
    public void checkOut(Patron patron, Librarian librarian, Document document) throws SQLException {
        loggingService.logString("CHECKOUT START\n" +
                "BY " + patron + "\n" +
                "VERIFIED BY " + librarian + "\n" +
                "DOCUMENT " + document);
        if (document.isOutstandingRequest()) {
            loggingService.logString("CHECKOUT FINISH\n" +
                    "RESULT " + "Can't check out this document because of outstanding request.");
            throw new NoSuchElementException("Can't check out this document because of outstanding request.");
        }
        if (!patron.getWaitingList().contains(document)) {
            loggingService.logString("CHECKOUT FINISH\n" +
                    "RESULT " + "This patron did't book this document.");
            throw new NoSuchElementException("This patron did't book this document.");
        }
        if (document.quantityOfFreeCopies() <= ((ArrayList) document.getBookedBy()).indexOf(patron)) {
            loggingService.logString("CHECKOUT FINISH\n" +
                    "RESULT " + "This patron can't take this document due to his place in queue and quantity of free copies.");
            throw new NoSuchElementException("This patron can't take this document due to his place in queue and " +
                    "quantity of free copies.");
        }

        Copy copy = document.getFreeCopy();
        copy.setCheckedOut(true);
        copy.setHolder(patron);
        copy.setCheckedOutDate(LocalDate.now());
        copy.setDueDate(LocalDate.now().plusWeeks(calcDueWeeks(patron, copy)));

        copy.getDocument().getBookedBy().remove(patron);
        patron.getWaitingList().remove(document);
        patron.getCheckedOutCopies().add(copy);

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
    public void renew(Patron patron, Copy copy) throws SQLException {
        loggingService.logString("RENEW START\n" +
                "BY " + patron + "\n" +
                "COPY " + copy);

        if (copy.getRenewTimes() > 1 && !patron.getType().equals("VP") || copy.getDocument().isOutstandingRequest()) {
            loggingService.logString("RENEW FINISH\n" +
                    "RESULT " + "This patron can't renew this document anymore.");
            throw new IllegalArgumentException("This patron can't renew this document anymore.");
        }

        copy.setRenewTimes(copy.getRenewTimes() + 1);
        copy.setCheckedOutDate(LocalDate.now());
        copy.setDueDate(LocalDate.now().plusWeeks(calcDueWeeks(patron, copy)));
        CopyDAO.update(copy);

        loggingService.logString("RENEW FINISH\n" +
                "RESULT " + "Patron renewed this copy.");

    }

    private int calcDueWeeks(Patron patron, Copy copy) {
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


    /**
     * Implements return logic.
     *
     * @param patron patron who returns the copy.
     * @param copy   copy which is going to be returned.
     * @throws SQLException
     */
    public void returnCopy(Librarian librarian, Patron patron, Copy copy) throws SQLException {
        loggingService.logString("RETURN START\n" +
                "BY " + patron + "\n" +
                "VERIFIED BY " + librarian + "\n" +
                "COPY " + copy);

        copy.setHolder(null);
        copy.setCheckedOut(false);
        copy.setRenewTimes(0);
        copy.setCheckedOutDate(null);
        copy.setDueDate(null);
        CopyDAO.update(copy);

        patron.getCheckedOutCopies().remove(copy);

        loggingService.logString("RETURN FINISHED\n" +
                "RESULT " + "Document was successfully returned.");
    }


    /**
     * Clean all queue for document, notifies bookers about this, ask checkouters for return, block document from new
     * booking.
     *
     * @param librarian
     * @param document
     * @throws SQLException
     */
    public void startOutstandingRequest(Librarian librarian, Document document) throws SQLException {
        loggingService.logString("START OUTSTANDING REQUEST START\n" +
                "LIBRARIAN " + librarian + "\n" +
                "DOCUMENT " + document + "\n");

        notifyService.notify(document.getBookedBy(), "Sorry your request for " + document.getTitle() +
                " was declined because of outstanding request. Can try to book this book again or ask librarian.");

        notifyService.notify(document.getCopies().stream()
                .map(Copy::getHolder)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()), "You should return " + document.getTitle() +
                " as soon as possible due to outstanding request. Sorry, ask librarian for more details.");

        String query = "DELETE FROM patron_booked_document WHERE document_id = ?;";
        PreparedStatement statement = database.getConnection().prepareStatement(query);
        statement.setInt(1, document.getId());
        statement.executeUpdate();
        database.getConnection().commit();
        document.setOutstandingRequest(true);

        loggingService.logString("START OUTSTANDING REQUEST FINISH\n" +
                "RESULT Queue for document cleaned, patrons who checked out book notified");
    }

    /**
     * Unblock document for booking.
     *
     * @param librarian
     * @param document
     * @throws SQLException
     */
    public void finishOutstandingRequest(Librarian librarian, Document document) throws SQLException {
        loggingService.logString("OUTSTANDING REQUEST Fi\n" +
                "LIBRARIAN " + librarian + "\n" +
                "DOCUMENT " + document + "\n");

        document.setOutstandingRequest(false);

        loggingService.logString("OUTSTANDING REQUEST FINISH\n" +
                "RESULT Queue for document cleaned, patrons who checked out book notified");
    }
}
