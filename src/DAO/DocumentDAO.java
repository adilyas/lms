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

    static private Database database;

    static void setDatabase(Database database) {
        DocumentDAO.database = database;
    }

    /**
     * Inserts document information to the appropriate database tables.
     *
     * @param document fields of this Document instance will be used to
     *                 insert new rows to the database tables.
     * @throws SQLException
     */
    static void insert(Document document) throws SQLException {
        String query = "INSERT INTO documents (type, title, value) " +
                "VALUES (?, ?, ?);";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setString(1, document.getType());
        st.setString(2, document.getTitle());
        st.setInt(3, document.getValue());
        st.executeUpdate();
        database.getConnection().commit();
        document.setId(getLastId());

        query = "INSERT INTO author_of_document VALUES(?, ?);";
        st = database.getConnection().prepareStatement(query);

        for (Author author : document.getAuthors()) {
            st.setInt(1, document.getId());
            st.setInt(2, author.getId());
            st.executeUpdate();
            database.getConnection().commit();
        }

        query = "INSERT INTO document_has_keyword VALUES(?, ?);";
        st = database.getConnection().prepareStatement(query);

        for (Keyword word : document.getKeywords()) {
            st.setInt(1, document.getId());
            st.setInt(2, word.getId());
            st.executeUpdate();
            database.getConnection().commit();
        }
    }

    /**
     * Deletes and information about the given documents.
     * In fact only document's id is used.
     *
     * @param document Document class' instance, should contain document.id
     *                 which document's id in the database.
     * @throws SQLException
     */
    static void delete(Document document) throws SQLException {
        String query = "DELETE FROM author_of_document WHERE document_id = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, document.getId());
        st.executeUpdate();
        database.getConnection().commit();

        query = "DELETE FROM document_has_keyword WHERE document_id = ?;";
        st = database.getConnection().prepareStatement(query);
        st.setInt(1, document.getId());
        st.executeUpdate();
        database.getConnection().commit();

        query = "DELETE FROM documents WHERE id = ?";
        st = database.getConnection().prepareStatement(query);
        st.setInt(1, document.getId());
        st.executeUpdate();
        database.getConnection().commit();
    }

    /**
     * Given the id returns Document class'
     * instance with filled all other fields.
     *
     * @param id document's id in the database.
     * @return Document  class' instance containing all information about the document
     * @throws SQLException
     */
    static Document get(int id) throws SQLException {
        String query = "SELECT * FROM documents WHERE id = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        database.getConnection().commit();
        if (!rs.next())
            throw new NoSuchElementException();
        Document result = new Document(rs.getInt("id"), rs.getString("type"), rs.getString("title"),
                rs.getInt("value"));

        query = "SELECT id, word FROM keywords JOIN document_has_keyword ON keyword_id = id WHERE document_id = ?";
        st = database.getConnection().prepareStatement(query);
        st.setInt(1, result.getId());
        rs = st.executeQuery();
        database.getConnection().commit();
        while (rs.next())
            result.getKeywords().add(new Keyword(rs.getInt("id"), rs.getString("word")));

        query = "SELECT id, name, surname FROM persons JOIN authors ON id = person_id" +
                " JOIN author_of_document ON person_id = author_id WHERE document_id = ?";
        st = database.getConnection().prepareStatement(query);
        st.setInt(1, result.getId());
        rs = st.executeQuery();
        database.getConnection().commit();
        while (rs.next())
            result.getAuthors().add(new Author(rs.getInt("id"), rs.getString("name"),
                    rs.getString("surname")));

        query = "SELECT p.id FROM persons p JOIN patron_booked_document pd ON p.id = pd.person_id " +
                "JOIN documents d ON d.id = pd.document_id WHERE document_id = ?" +
                "ORDER BY priority, date ASC;";
        st = database.getConnection().prepareStatement(query);
        st.setInt(1, result.getId());
        rs = st.executeQuery();
        database.getConnection().commit();
        while (rs.next())
            result.getBookedBy().add(PatronDAO.get(rs.getInt("id")));

        return result;
    }

    /**
     * Updates existing data in the database.
     * Resets all the fields like in insert, but saves table's structure and id.
     *
     * @param document instance containing new values to update.
     * @throws SQLException
     */
    static void update(Document document) throws SQLException {
        String query = "UPDATE documents SET type = ?, title = ?, value = ? WHERE id = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setString(1, document.getType());
        st.setString(2, document.getTitle());
        st.setInt(3, document.getValue());
        st.setInt(4, document.getId());
        st.executeUpdate();
        database.getConnection().commit();

        query = "DELETE FROM author_of_document WHERE document_id = ?;";
        st = database.getConnection().prepareStatement(query);
        st.setInt(1, document.getId());
        st.executeUpdate();
        database.getConnection().commit();

        query = "INSERT INTO author_of_document VALUES(?, ?);";
        st = database.getConnection().prepareStatement(query);

        for (Author author : document.getAuthors()) {
            st.setInt(1, document.getId());
            st.setInt(2, author.getId());
            st.executeUpdate();
            database.getConnection().commit();
        }

        query = "DELETE FROM document_has_keyword WHERE document_id = ?;";
        st = database.getConnection().prepareStatement(query);
        st.setInt(1, document.getId());
        st.executeUpdate();
        database.getConnection().commit();

        query = "INSERT INTO document_has_keyword VALUES(?, ?);";
        st = database.getConnection().prepareStatement(query);

        for (Keyword word : document.getKeywords()) {
            st.setInt(1, document.getId());
            st.setInt(2, word.getId());
            st.executeUpdate();
            database.getConnection().commit();
        }
    }

    /**
     * Returns the last added id in the documents table.
     *
     * @return last added row's id.
     * @throws SQLException
     */
    static private int getLastId() throws SQLException {
        String query = "SELECT LAST_INSERT_ID() FROM documents;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        ResultSet rs = st.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
}
