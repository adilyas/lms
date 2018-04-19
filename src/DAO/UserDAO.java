package DAO;

import Database.Database;
import Objects.Person;
import Objects.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class UserDAO {

    static private Database db;

    static void setDb(Database dbb) {
        db = dbb;
    }

    static void insert(User user) throws SQLException {
        PersonDAO.insert(user);

        String query = "INSERT INTO users (person_id, type, phone_number, address) " +
                "VALUES (?, ?, ?, ?);";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setInt(1, user.getId());
        st.setString(2, user.getType());
        st.setString(3, user.getPhoneNumber());
        st.setString(4, user.getAddress());
        st.executeUpdate();
        db.getConnection().commit();
    }

    static void delete(User user) throws SQLException {
        PersonDAO.delete(user);

        String query = "DELETE FROM users WHERE person_id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setInt(1, user.getId());
        st.executeUpdate();
        db.getConnection().commit();
    }

    static User get(int id) throws SQLException {
        Person person = PersonDAO.get(id);

        String query = "SELECT * FROM users WHERE person_id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        db.getConnection().commit();
        if (rs.next()) {
            return new User(person.getId(), person.getName(), person.getSurname(), rs.getString("type"), rs.getString("phone_number"),
                    rs.getString("address"));
        } else {
            throw new NoSuchElementException();
        }
    }

    static void update(User user) throws SQLException {
        PersonDAO.update(user);

        String query = "UPDATE users SET " +
                "type = ?" +
                "phone_number = ?, " +
                "address = ? WHERE person_id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setString(1, user.getType());
        st.setString(2, user.getPhoneNumber());
        st.setString(3, user.getAddress());
        st.setInt(4, user.getId());
        st.executeUpdate();
        db.getConnection().commit();
    }
}
