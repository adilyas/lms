package DAO;

import Database.Database;
import Objects.Author;
import Objects.Document;
import Objects.Keyword;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

public class DocumentDAO {

    static private Database db;

    static void setDb(Database dbb) {
        db = dbb;
    }

    static Document get(int ID) throws SQLException {
        String query = "select * from documents where id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setInt(1, ID);
        ResultSet rs = st.executeQuery();
        db.getConnection().commit();
        if(!rs.next())
            throw  new NoSuchElementException();
        Document result = new Document(rs.getInt("id"), rs.getString("title"),
                rs.getInt("value"), new ArrayList<Author>(), new ArrayList<Keyword>());

        query = "select id, word from keywords join document_has_keyword on keyword_id = id where document_id = ?";
        st = db.getConnection().prepareStatement(query);
        st.setInt(1, result.getId());
        rs = st.executeQuery();
        db.getConnection().commit();
        while(rs.next())
            result.getKeywords().add(new Keyword(rs.getInt("id"), rs.getString("word")));

        query = "select id, name, surname from persons join authors on id = person_id" +
                " join author_of_document ON person_id = author_id where document_id = ?";
        st = db.getConnection().prepareStatement(query);
        st.setInt(1, result.getId());
        rs = st.executeQuery();
        db.getConnection().commit();
        while(rs.next())
            result.getAuthors().add(new Author(rs.getInt("id"), rs.getString("name"),
                    rs.getString("surname")));

        return result;
    }

    static void insert(Document document) throws SQLException {
        String query = "insert into documents (title, value) " +
                "values (?, ?);";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setString(1, document.getTitle());
        st.setInt(2, document.getValue());
        st.executeUpdate();
        db.getConnection().commit();
        document.setId(getLastId());

        query = "insert into author_of_document values(?, ?);";
        st = db.getConnection().prepareStatement(query);

        for (Author author : document.getAuthors()) {
            st.setInt(1, document.getId());
            st.setInt(2, author.getId());
            st.executeUpdate();
            db.getConnection().commit();
        }

        query = "insert into document_has_keyword values(?, ?);";
        st = db.getConnection().prepareStatement(query);

        for (Keyword word : document.getKeywords()) {
            st.setInt(1, document.getId());
            st.setInt(2, word.getId());
            st.executeUpdate();
            db.getConnection().commit();
        }
    }

    static void update(Document document) throws SQLException {
        String query = "update documents set title = ?, value = ? where id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setInt(1, document.getId());
        st.executeUpdate();
        db.getConnection().commit();
        document.setId(getLastId());

        query = "delete from author_of_document where document_id = ?;";
        st = db.getConnection().prepareStatement(query);
        st.setInt(1, document.getId());
        st.executeUpdate();
        db.getConnection().commit();

        query = "insert into author_of_document values(?, ?);";
        st = db.getConnection().prepareStatement(query);

        for (Author author : document.getAuthors()) {
            st.setInt(1, document.getId());
            st.setInt(2, author.getId());
            st.executeUpdate();
            db.getConnection().commit();
        }

        query = "delete from document_has_keyword where document_id = ?;";
        st = db.getConnection().prepareStatement(query);
        st.setInt(1, document.getId());
        st.executeUpdate();
        db.getConnection().commit();

        query = "insert into document_has_keyword values(?, ?);";
        st = db.getConnection().prepareStatement(query);

        for (Keyword word : document.getKeywords()) {
            st.setInt(1, document.getId());
            st.setInt(2, word.getId());
            st.executeUpdate();
            db.getConnection().commit();
        }
    }

    static void delete(Document document) throws SQLException {
        String query = "delete from documents where id = ?";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setInt(1, document.getId());
        st.executeUpdate();
        db.getConnection().commit();

        query = "delete from author_of_document where document_id = ?;";
        st = db.getConnection().prepareStatement(query);
        st.setInt(1, document.getId());
        st.executeUpdate();
        db.getConnection().commit();

        query = "delete from document_has_keyword where document_id = ?;";
        st = db.getConnection().prepareStatement(query);
        st.setInt(1, document.getId());
        st.executeUpdate();
        db.getConnection().commit();
    }

    static private int getLastId() throws SQLException {
        String query = "select LAST_INSERT_ID() from documents;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        ResultSet rs = st.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
}
