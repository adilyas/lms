package DAO;

import Database.Database;
import Objects.Author;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class AuthorDAO {

    static private Database database;

    public static void setDatabase(Database database) {
        AuthorDAO.database = database;
    }

    static Author get(int id) throws SQLException {
        String query = "SELECT * FROM persons JOIN authors ON persons.id = authors.person_id WHERE id = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        database.getConnection().commit();
        if (rs.next()) {
            return new Author(rs.getInt("id"), rs.getString("name"), rs.getString("surname"));
        } else {
            throw new NoSuchElementException();
        }
    }

    static Author getByName(Author author) throws SQLException {
        String query = "SELECT * FROM persons JOIN authors ON persons.id = authors.person_id WHERE name = ? AND surname = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setString(1, author.getName());
        st.setString(2, author.getSurname());
        ResultSet rs = st.executeQuery();
        database.getConnection().commit();
        if (rs.next()) {
            return new Author(rs.getInt("id"), rs.getString("name"), rs.getString("surname"));
        } else {
            throw new NoSuchElementException();
        }
    }


    static void insert(Author author) throws SQLException {
        PersonDAO.insert(author);
        String query = "INSERT INTO authors (id) " +
                "VALUES (?);";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, author.getId());
        st.executeUpdate();
        database.getConnection().commit();
    }

    static void update(Author author) throws SQLException {
        PersonDAO.update(author);
    }

    static void delete(Author author) throws SQLException {
        String docAddQuery = "DELETE FROM authors WHERE id = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(docAddQuery);
        st.setInt(1, author.getId());
        st.executeUpdate();
        database.getConnection().commit();

        PersonDAO.delete(author);
    }
}
