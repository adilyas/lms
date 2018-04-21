package DAO;

import Database.Database;
import Objects.Author;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class AuthorDAO {

    static private Database db;

    static void setConnection(Database dbb) {
        db = dbb;
    }

    static Author get(int id) throws SQLException {
        String query = "SELECT * FROM persons JOIN authors ON persons.id = authors.person_id WHERE id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        db.getConnection().commit();
        if (rs.next()) {
            return new Author(rs.getInt("id"), rs.getString("name"), rs.getString("surname"));
        } else {
            throw new NoSuchElementException();
        }
    }

    static Author getByName(Author author) throws SQLException {
        String query = "SELECT * FROM persons JOIN authors ON persons.id = authors.person_id WHERE name = ? AND surname = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setString(1, author.getName());
        st.setString(2, author.getSurname());
        ResultSet rs = st.executeQuery();
        db.getConnection().commit();
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
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setInt(1, author.getId());
        st.executeUpdate();
        db.getConnection().commit();
    }

    static void update(Author author) throws SQLException {
        PersonDAO.update(author);
    }

    static void delete(Author author) throws SQLException {
        String docAddQuery = "DELETE FROM authors WHERE id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(docAddQuery);
        st.setInt(1, author.getId());
        st.executeUpdate();
        db.getConnection().commit();

        PersonDAO.delete(author);
    }
}
