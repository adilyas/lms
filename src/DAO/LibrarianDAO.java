package DAO;

import Database.Database;
import Objects.Librarian;
import Objects.Person;
import Objects.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class LibrarianDAO {

    static private Database db;

    static void setDb(Database dbb) {
        db = dbb;
    }

    static void insert(Librarian librarian) throws SQLException {
        PersonDAO.insert(librarian);

        String query = "INSERT INTO librarians (person_id) " +
                "VALUES (?);";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setInt(1, librarian.getId());
        st.executeUpdate();
        db.getConnection().commit();
    }

    static void delete(Librarian librarian) throws SQLException {
        PersonDAO.delete(librarian);

        String query = "DELETE FROM librarians WHERE person_id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setInt(1, librarian.getId());
        st.executeUpdate();
        db.getConnection().commit();
    }

    static Librarian get(int id) throws SQLException {
        Person person = PersonDAO.get(id);

        String query = "SELECT 1 FROM librarians WHERE person_id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        db.getConnection().commit();
        if (rs.next()) {
            return (Librarian) person;
        } else {
            throw new NoSuchElementException();
        }
    }

    static void update(Librarian librarian) throws SQLException {
        PersonDAO.update(librarian);
    }
}
