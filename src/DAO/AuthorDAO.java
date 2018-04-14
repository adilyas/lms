package DAO;

import Database.Database;
import Objects.Author;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class AuthorDAO {

    static private Database db;

    static void setDb(Database dbb) {
        db = dbb;
    }

    static Author get(int ID) throws SQLException {
        String query = "select * from persons join authors on persons.id = authors.id where id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setInt(1, ID);
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
        String query = "insert into authors (id) " +
                "values (?);";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setInt(1, author.getId());
        st.executeUpdate();
        db.getConnection().commit();
    }

    static void update(Author author) throws SQLException {
        PersonDAO.update(author);
    }

    static void delete(Author author) throws SQLException {
        PersonDAO.delete(author);
        String docAddQuery = "delete from authors where id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(docAddQuery);
        st.setInt(1, author.getId());
        st.executeUpdate();
        db.getConnection().commit();
    }

    static private int getLastId() throws SQLException {
        String query = "select LAST_INSERT_ID() from ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setString(1, "authors");
        ResultSet rs = st.executeQuery();
        rs.next();

        return rs.getInt(1);
    }
}
