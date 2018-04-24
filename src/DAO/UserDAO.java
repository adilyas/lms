package DAO;

import Database.Database;
import Objects.Person;
import Objects.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class UserDAO {

    static private Database database;

    public static void setDatabase(Database database) {
        UserDAO.database = database;
    }

    /**
     * Inserts user's information to the appropriate database tables.
     *
     * @param user fields of this User class' instance will be used to
     *                 insert new rows to the database tables.
     * @throws SQLException
     */
    static void insert(User user) throws SQLException {
        PersonDAO.insert(user);

        String query = "INSERT INTO users (person_id, type, phone_number, address, email) " +
                "VALUES (?, ?, ?, ?, ?);";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, user.getId());
        st.setString(2, user.getType());
        st.setString(3, user.getPhoneNumber());
        st.setString(4, user.getAddress());
        st.setString(5, user.getEmail());
        st.executeUpdate();

    }

    /**
     * Deletes and information about the given users.
     * In fact only user's id is used.
     *
     * @param user User class' instance, should contain document.id
     *                 which document's id in the database.
     * @throws SQLException
     */
    static void delete(User user) throws SQLException {
        String query = "DELETE FROM users WHERE person_id = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, user.getId());
        st.executeUpdate();


        PersonDAO.delete(user);
    }

    /**
     * Given the id returns User class'
     * instance with filled all other fields.
     *
     * @param id user's id in the database.
     * @return User class' instance containing all information about the user.
     * @throws SQLException
     */
    static User get(int id) throws SQLException {
        Person person = PersonDAO.get(id);

        String query = "SELECT * FROM users WHERE person_id = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            return new User(person.getId(), person.getName(), person.getSurname(), rs.getString("type"), rs.getString("phone_number"),
                    rs.getString("address"), rs.getString("email"));
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Updates existing data in the database.
     * Resets all the fields like in insert, but saves table's structure and id.
     *
     * @param user instance containing new values to update.
     * @throws SQLException
     */
    static void update(User user) throws SQLException {
        PersonDAO.update(user);

        String query = "UPDATE users SET " +
                "type = ?, " +
                "phone_number = ?, " +
                "address = ?, " +
                "email = ? " +
                "WHERE person_id = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setString(1, user.getType());
        st.setString(2, user.getPhoneNumber());
        st.setString(3, user.getAddress());
        st.setString(4, user.getEmail());
        st.setInt(5, user.getId());
        st.executeUpdate();

    }
}
