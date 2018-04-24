package DAO;

import Database.Database;
import Objects.Librarian;
import Objects.Person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class LibrarianDAO {

    static private Database database;

    public static void setDatabase(Database database) {
        LibrarianDAO.database = database;
    }

    public static void insert(Librarian librarian) throws SQLException {
        PersonDAO.insert(librarian);

        String query = "INSERT INTO librarians (person_id) " +
                "VALUES (?);";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, librarian.getId());
        st.executeUpdate();

    }

    static void delete(Librarian librarian) throws SQLException {
        String query = "DELETE FROM librarians WHERE person_id = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, librarian.getId());
        st.executeUpdate();


        PersonDAO.delete(librarian);
    }

    static Librarian get(int id) throws SQLException {
        Person person = PersonDAO.get(id);

        String query = "SELECT 1 FROM librarians WHERE person_id = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            return (Librarian) person;
        } else {
            throw new NoSuchElementException();
        }
    }

    public static void update(Librarian librarian) throws SQLException {
        PersonDAO.update(librarian);
    }
}
