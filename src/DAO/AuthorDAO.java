package DAO;

import Database.Database;
import Objects.Author;
import Objects.Keyword;

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

        if (rs.next()) {
            return new Author(rs.getInt("id"), rs.getString("name"), rs.getString("surname"));
        } else {
            throw new NoSuchElementException();
        }
    }

    public static Author getByNameOrCreate(Author author) throws SQLException {
        String query = "SELECT * FROM persons JOIN authors ON persons.id = authors.person_id WHERE name = ? AND surname = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setString(1, author.getName());
        st.setString(2, author.getSurname());
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            return new Author(rs.getInt("id"), rs.getString("name"), rs.getString("surname"));
        } else {
            AuthorDAO.insert(author);
            return author;
        }
    }

    static void insert(Author author) throws SQLException {
        PersonDAO.insert(author);
        String query = "INSERT INTO authors (person_id) " +
                "VALUES (?);";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, author.getId());
        st.executeUpdate();
    }

    static void update(Author author) throws SQLException {
        PersonDAO.update(author);
    }

    static void delete(Author author) throws SQLException {
        String docAddQuery = "DELETE FROM authors WHERE id = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(docAddQuery);
        st.setInt(1, author.getId());
        st.executeUpdate();


        PersonDAO.delete(author);
    }
}
