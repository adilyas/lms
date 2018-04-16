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

    static Person get(int ID) throws SQLException {
        String query = "select * from persons where id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setInt(1, ID);
        ResultSet rs = st.executeQuery();
        db.getConnection().commit();
        if (rs.next()) {
            return new Person(rs.getInt("id"), rs.getString("name"), rs.getString("surname"));
        } else {
            throw new NoSuchElementException();
        }
    }

    static Person getByName(Person person) throws SQLException {
        String query = "select * from persons where name = ? and surname = ?;";
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
        String query = "insert into persons (name, surname) " +
                "values (?, ?);";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setString(1, person.getName());
        st.setString(2, person.getSurname());
        st.executeUpdate();
        db.getConnection().commit();
        person.setId(PersonDAO.getLastId());
    }

    static void update(Person person) throws SQLException {
        String query = "update persons set" +
                "name = ?" +
                "suname = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setString(1, person.getName());
        st.setString(2, person.getSurname());
        st.executeUpdate();
        db.getConnection().commit();
    }

    static void delete(Person person) throws SQLException {
        String query = "delete from persons where id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setInt(1, person.getId());
        st.executeUpdate();
        db.getConnection().commit();
    }

    static int getLastId() throws SQLException {
        String query = "select LAST_INSERT_ID() from ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setString(1, "persons");
        ResultSet rs = st.executeQuery();
        rs.next();

        return rs.getInt(1);
    }
}
