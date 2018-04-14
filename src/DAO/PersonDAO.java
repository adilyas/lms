package DAO;

import Database.Database;
import Objects.Author;
import Objects.Person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class PersonDAO {

    static private Database db;

    static void setDb(Database dbb){
        db = db;
    }

    static Person get(int ID) throws SQLException {
        String docAddQuery = "select in persons where id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(docAddQuery);
        st.setInt(1, ID);
        ResultSet rs = st.executeQuery();
        db.getConnection().commit();
        rs.next();
        return new Person(rs.getInt("id"), rs.getString("name"), rs.getString("surname"));
    }


    static void insert(Person person) throws SQLException {
        String docAddQuery = "insert into persons (name, surname) " +
                "values (?, ?);";
        PreparedStatement st = db.getConnection().prepareStatement(docAddQuery);
        st.setString(1, person.getName());
        st.setString(2, person.getSurname());
        st.executeUpdate();
        db.getConnection().commit();
        person.setId(PersonDAO.getLastId());
    }

    static void update (Person person) throws SQLException {
        String docAddQuery = "update persons set" +
                "name = ?" +
                "suname = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(docAddQuery);
        st.setString(1, person.getName());
        st.setString(2, person.getSurname());
        st.executeUpdate();
        db.getConnection().commit();
    }

    static void delete (Person person) throws SQLException {
        String docAddQuery = "delete in persons where id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(docAddQuery);
        st.setInt(1, person.getId());
        st.executeUpdate();
        db.getConnection().commit();
    }

    static private int getLastId() throws SQLException {
        String query = "select LAST_INSERT_ID() from ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setString(1, "persons");
        ResultSet rs = st.executeQuery();
        rs.next();

        return rs.getInt(1);
    }
}
