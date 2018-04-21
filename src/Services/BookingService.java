package Services;

import DAO.LibrarianDAO;
import DAO.PatronDAO;
import Database.Database;
import Objects.*;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.NoSuchElementException;

public class BookingService {
    static private Database db;

    static void setDb(Database dbb) {
        db = dbb;
    }

    /**
     * Implements document booking logic.
     * Depending on user type sets the priority of the user in the order.
     *
     * @param patron booking patron
     * @param document booking document
     * @throws SQLException
     */
    public static void book(Patron patron, Document document) throws SQLException {
        String insertText = "insert into lms.patron_booked_document " +
                "(person_id, document_id, priority, date) values (?, ?, ?, ?);";
        PreparedStatement st = db.getConnection().prepareStatement(insertText);

//        less priority value means more important the user is
        int priority;
        switch (patron.getType()) {
            case "student":
                priority = 1;
                break;
            case "instructor":
                priority = 2;
                break;
            case "professor":
                priority = 5;
                break;
            case "TA":
                priority = 3;
                break;
            case "VP":
                priority = 4;
                break;
            default:
                throw new NoSuchElementException("Wrong type; " +
                        "Also BookingService.book doesn't support librarian types");
        }

        LocalDate currentDate = LocalDate.now();

        st.setInt(1, patron.getId());
        st.setInt(2, document.getId());
        st.setInt(3, priority);
        st.setDate(4, Date.valueOf(currentDate.toString()));
        st.executeUpdate();
        db.getConnection().commit();
    }

    /**
     * Implements checking out logic.
     * Depending on user type and document type sets dueDate.
     *
     * @param patron  checking out patron
     * @param librarian librarian who has
     *                  let patron to check out the document
     * @param copy particular copy the patring is checking out
     * @throws SQLException
     */
    public static void checkOut(Patron patron, Librarian librarian, Copy copy) throws SQLException {
        if (!isAvailable(copy.getDocument())) {
            return; // may be it's better to write some feedback here
        }
        String updateText = "update copies set is_checked_out = ?, " +
                "holder_id = ?, renew_times = renew_times + 1, check_out_date = ?, " +
                "due_date = ? where id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(updateText);

        int dueWeeks = calcDueWeeks(patron, copy);
        LocalDate currentDate = LocalDate.now();
        LocalDate dueDate = currentDate.plusWeeks(dueWeeks);

        st.setInt(1, 1);
        st.setInt(2, patron.getId());
        st.setDate(3, Date.valueOf(currentDate));
        st.setDate(4, Date.valueOf(dueDate));
        st.executeUpdate();
        db.getConnection().commit();
    }

    /**
     * Renews patron checked out copy relation considering
     * required conditions. Updates appropriate fields in the database.
     *
     * @param patron patron who wants to renew
     * @param copy copy which is going to be renewed
     * @throws SQLException
     */
    public static void renew(Patron patron, Copy copy) throws SQLException {
        if (isOutstandingRequest(copy.getDocument())) {
            return; // may be it's better to write some feedback here
        }
        if (copy.getRenewTimes() > 1 && !patron.getType().equals("VP")) {
            return; // may be it's better to write some feedback here
        }

        String updateText = "update copies set renew_times = renew_times + 1, check_out_date = ?, due_date = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(updateText);

        int dueWeeks = calcDueWeeks(patron, copy);
        LocalDate newCheckOutDate = copy.getDueDate();
        LocalDate newDueDate = newCheckOutDate.plusWeeks(dueWeeks);

        st.setDate(1, Date.valueOf(newCheckOutDate));
        st.setDate(2, Date.valueOf(newDueDate));
        st.executeUpdate();
        db.getConnection().commit();
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

    private static Boolean isOutstandingRequest(Document document) throws SQLException {
        // I've made it in a naive way, but it's easy to fix
        String queryText = "select count(*) from patron_booked_document " +
                "where document_id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(queryText);
        ResultSet rs = st.executeQuery();
        rs.next();
        return rs.getInt("count(*)") > 0;
    }

    private static Boolean isAvailable(Document document) throws SQLException {
        String queryText = "select count(*) from copies where document_id = ? " +
                "and is_checked_out = 0";
        PreparedStatement st = db.getConnection().prepareStatement(queryText);
        st.setInt(1, document.getId());
        ResultSet rs = st.executeQuery();
        rs.next();
        return rs.getInt("count(*)") > 0;
    }
}
