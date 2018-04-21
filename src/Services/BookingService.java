package Services;

import DAO.LibrarianDAO;
import DAO.PatronDAO;
import Database.Database;
import Objects.Document;
import Objects.Librarian;
import Objects.Patron;
import Objects.User;

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

    public static void book(Patron patron, Document document) throws SQLException {
        String insertText = "insert into copies " +
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
}
