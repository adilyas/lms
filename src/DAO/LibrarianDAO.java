package DAO;

import Database.Database;
import Objects.Librarian;
import Objects.Person;
import Objects.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class LibrarianDAO {

    static private Database database;

    public static void setDatabase(Database database) {
        LibrarianDAO.database = database;
    }

    public static void insert(Librarian librarian) throws SQLException {
        UserDAO.insert(librarian);

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
        User user = UserDAO.get(id);

        String query = "SELECT 1 FROM librarians WHERE person_id = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            return (Librarian) user;
        } else {
            throw new NoSuchElementException();
        }
    }

    public static Librarian getByEmail(String email) throws SQLException {
        User user = UserDAO.getByEmail(email);

        String query = "SELECT 1 FROM librarians WHERE person_id = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, user.getId());
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            return new Librarian(user.getId(), user.getName(), user.getSurname(), user.getType(), user.getPhoneNumber(),
                    user.getAddress(), user.getEmail());
        } else {
            throw new NoSuchElementException();
        }
    }

    public static void update(Librarian librarian) throws SQLException {
        UserDAO.update(librarian);
    }

}
