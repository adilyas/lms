package Services;

import DAO.LibrarianDAO;
import DAO.PatronDAO;
import Database.Database;
import Objects.*;

import java.sql.Date;
import java.sql.PreparedStatement;
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
        String updateText = "update copies set is_checked_out = ?, " +
                "holder_id = ?, renew_times = renew_times + 1, check_out_date = ?, due_date = ? where id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(updateText);

        LocalDate currentDate = LocalDate.now();

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

        LocalDate dueDate = currentDate.plusWeeks(dueWeeks);

        st.setInt(1, 1);
        st.setInt(2, patron.getId());
        st.setDate(3, Date.valueOf(currentDate));
        st.setDate(4, Date.valueOf(dueDate));
        st.executeUpdate();
        db.getConnection().commit();
    }
}
