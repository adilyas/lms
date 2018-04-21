package DAO;

import Database.Database;
import Objects.Author;
import Objects.Document;
import Objects.Keyword;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class DocumentDAO {

    static private Database db;

    static void setDb(Database dbb) {
        db = dbb;
    }

    static void insert(Document document) throws SQLException {
        String query = "INSERT INTO documents (type, title, value) " +
                "VALUES (?, ?, ?);";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setString(1, document.getType());
        st.setString(2, document.getTitle());
        st.setInt(3, document.getValue());
        st.executeUpdate();
        db.getConnection().commit();
        document.setId(getLastId());

        query = "INSERT INTO author_of_document VALUES(?, ?);";
        st = db.getConnection().prepareStatement(query);

        for (Author author : document.getAuthors()) {
            st.setInt(1, document.getId());
            st.setInt(2, author.getId());
            st.executeUpdate();
            db.getConnection().commit();
        }

        query = "INSERT INTO document_has_keyword VALUES(?, ?);";
        st = db.getConnection().prepareStatement(query);

        for (Keyword word : document.getKeywords()) {
            st.setInt(1, document.getId());
            st.setInt(2, word.getId());
            st.executeUpdate();
            db.getConnection().commit();
        }
    }

    static void delete(Document document) throws SQLException {
        String query = "DELETE FROM author_of_document WHERE document_id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setInt(1, document.getId());
        st.executeUpdate();
        db.getConnection().commit();

        query = "DELETE FROM document_has_keyword WHERE document_id = ?;";
        st = db.getConnection().prepareStatement(query);
        st.setInt(1, document.getId());
        st.executeUpdate();
        db.getConnection().commit();

        query = "DELETE FROM documents WHERE id = ?";
        st = db.getConnection().prepareStatement(query);
        st.setInt(1, document.getId());
        st.executeUpdate();
        db.getConnection().commit();
    }

    static Document get(int id) throws SQLException {
        String query = "SELECT * FROM documents WHERE id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        db.getConnection().commit();
        if (!rs.next())
            throw new NoSuchElementException();
        Document result = new Document(rs.getInt("id"), rs.getString("type"), rs.getString("title"),
                rs.getInt("value"));

        query = "SELECT id, word FROM keywords JOIN document_has_keyword ON keyword_id = id WHERE document_id = ?";
        st = db.getConnection().prepareStatement(query);
        st.setInt(1, result.getId());
        rs = st.executeQuery();
        db.getConnection().commit();
        while (rs.next())
            result.getKeywords().add(new Keyword(rs.getInt("id"), rs.getString("word")));

        query = "SELECT id, name, surname FROM persons JOIN authors ON id = person_id" +
                " JOIN author_of_document ON person_id = author_id WHERE document_id = ?";
        st = db.getConnection().prepareStatement(query);
        st.setInt(1, result.getId());
        rs = st.executeQuery();
        db.getConnection().commit();
        while (rs.next())
            result.getAuthors().add(new Author(rs.getInt("id"), rs.getString("name"),
                    rs.getString("surname")));

        query = "SELECT p.id FROM persons p JOIN patron_booked_document pd ON p.id = pd.person_id " +
                "JOIN documents d ON d.id = pd.document_id WHERE document_id = ?" +
                "ORDER BY priority, date ASC;";
        st = db.getConnection().prepareStatement(query);
        st.setInt(1, result.getId());
        rs = st.executeQuery();
        db.getConnection().commit();
        while (rs.next())
            result.getBookedBy().add(PatronDAO.get(rs.getInt("id")));

        return result;
    }

    static void update(Document document) throws SQLException {
        String query = "UPDATE documents SET type = ?, title = ?, value = ? WHERE id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setString(1, document.getType());
        st.setString(2, document.getTitle());
        st.setInt(3, document.getValue());
        st.setInt(4, document.getId());
        st.executeUpdate();
        db.getConnection().commit();

        query = "DELETE FROM author_of_document WHERE document_id = ?;";
        st = db.getConnection().prepareStatement(query);
        st.setInt(1, document.getId());
        st.executeUpdate();
        db.getConnection().commit();

        query = "INSERT INTO author_of_document VALUES(?, ?);";
        st = db.getConnection().prepareStatement(query);

        for (Author author : document.getAuthors()) {
            st.setInt(1, document.getId());
            st.setInt(2, author.getId());
            st.executeUpdate();
            db.getConnection().commit();
        }

        query = "DELETE FROM document_has_keyword WHERE document_id = ?;";
        st = db.getConnection().prepareStatement(query);
        st.setInt(1, document.getId());
        st.executeUpdate();
        db.getConnection().commit();

        query = "INSERT INTO document_has_keyword VALUES(?, ?);";
        st = db.getConnection().prepareStatement(query);

        for (Keyword word : document.getKeywords()) {
            st.setInt(1, document.getId());
            st.setInt(2, word.getId());
            st.executeUpdate();
            db.getConnection().commit();
        }
    }

    static private int getLastId() throws SQLException {
        String query = "SELECT LAST_INSERT_ID() FROM documents;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        ResultSet rs = st.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
}
