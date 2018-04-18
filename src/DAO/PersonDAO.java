package DAO;

import Database.Database;
import Objects.Person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class PersonDAO {

    static private Database db;

    static void setDb(Database dbb) {
        db = dbb;
    }

    static Person get(int id) throws SQLException {
        String query = "SELECT * FROM persons WHERE id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        db.getConnection().commit();
        if (rs.next()) {
            return new Person(rs.getInt("id"), rs.getString("name"), rs.getString("surname"));
        } else {
            throw new NoSuchElementException();
        }
    }

    static Person getByName(Person person) throws SQLException {
        String query = "SELECT * FROM persons WHERE name = ? AND surname = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setString(1, person.getName());
        st.setString(2, person.getSurname());
        ResultSet rs = st.executeQuery();
        db.getConnection().commit();
        if (rs.next()) {
            return new Person(rs.getInt("id"), rs.getString("name"), rs.getString("surname"));
        } else {
            throw new NoSuchElementException();
        }
    }

    static void insert(Person person) throws SQLException {
        String query = "INSERT INTO persons (name, surname) " +
                "VALUES (?, ?);";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setString(1, person.getName());
        st.setString(2, person.getSurname());
        st.executeUpdate();
        db.getConnection().commit();
        person.setId(PersonDAO.getLastId());
    }

    static void update(Person person) throws SQLException {
        String query = "UPDATE persons set " +
                "name = ?" +
                "suname = ? WHERE id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setString(1, person.getName());
        st.setString(2, person.getSurname());
        st.setInt(3, person.getId());
        st.executeUpdate();
        db.getConnection().commit();
    }

    static void delete(Person person) throws SQLException {
        String query = "DELETE FROM persons WHERE id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setInt(1, person.getId());
        st.executeUpdate();
        db.getConnection().commit();
    }

    static int getLastId() throws SQLException {
        String query = "SELECT LAST_INSERT_ID() FROM persons;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        ResultSet rs = st.executeQuery();
        rs.next();

        return rs.getInt(1);
    }
}
