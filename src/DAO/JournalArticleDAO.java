package DAO;

import Database.Database;
import Objects.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class JournalArticleDAO {

    static private Database database;

    static void setDatabase(Database database) {
        JournalArticleDAO.database = database;
    }

    static void insert(JournalArticle journalArticle) throws SQLException {
        String query = "INSERT INTO journal_articles (issue_id, title) " +
                "VALUES (?, ?);";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, journalArticle.getJournalIssue().getId());
        st.setString(2, journalArticle.getTitle());
        st.executeUpdate();
        database.getConnection().commit();
        journalArticle.setId(getLastId());

        query = "INSERT INTO author_of_article VALUES(?, ?);";
        st = database.getConnection().prepareStatement(query);

        for (Author author : journalArticle.getAuthors()) {
            st.setInt(1, journalArticle.getId());
            st.setInt(2, author.getId());
            st.executeUpdate();
            database.getConnection().commit();
        }

        query = "INSERT INTO article_has_keyword VALUES(?, ?);";
        st = database.getConnection().prepareStatement(query);

        for (Keyword word : journalArticle.getKeywords()) {
            st.setInt(1, journalArticle.getId());
            st.setInt(2, word.getId());
            st.executeUpdate();
            database.getConnection().commit();
        }
    }

    static void delete(JournalArticle journalArticle) throws SQLException {
        String query = "DELETE FROM journal_articles WHERE id = ?";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, journalArticle.getId());
        st.executeUpdate();
        database.getConnection().commit();

        query = "DELETE FROM author_of_article WHERE article_id = ?;";
        st = database.getConnection().prepareStatement(query);
        st.setInt(1, journalArticle.getId());
        st.executeUpdate();
        database.getConnection().commit();

        query = "DELETE FROM article_has_keyword WHERE article_id = ?;";
        st = database.getConnection().prepareStatement(query);
        st.setInt(1, journalArticle.getId());
        st.executeUpdate();
        database.getConnection().commit();
    }

    static JournalArticle get(int id) throws SQLException {
        String query = "SELECT * FROM journal_articles WHERE id = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        database.getConnection().commit();
        if (!rs.next())
            throw new NoSuchElementException();
        JournalArticle journalArticle = new JournalArticle(rs.getInt("id"), rs.getString("title"),
                JournalIssueDAO.get(rs.getInt("document_id")), new ArrayList<Author>(), new ArrayList<Keyword>());

        query = "SELECT id, word FROM keywords k JOIN article_has_keyword a ON k.id = a.keyword_id WHERE article_id = ?";
        st = database.getConnection().prepareStatement(query);
        st.setInt(1, journalArticle.getId());
        rs = st.executeQuery();
        database.getConnection().commit();
        while (rs.next())
            journalArticle.getKeywords().add(new Keyword(rs.getInt("id"), rs.getString("word")));

        query = "SELECT id, name, surname FROM persons JOIN authors ON id = person_id" +
                " JOIN author_of_article ON person_id = author_id WHERE article_id = ?";
        st = database.getConnection().prepareStatement(query);
        st.setInt(1, journalArticle.getId());
        rs = st.executeQuery();
        database.getConnection().commit();
        while (rs.next())
            journalArticle.getAuthors().add(new Author(rs.getInt("id"), rs.getString("name"),
                    rs.getString("surname")));

        return journalArticle;
    }

    static void update(JournalArticle journalArticle) throws SQLException {
        String query = "UPDATE journal_articles SET issue_id = ?, title = ? WHERE id = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, journalArticle.getJournalIssue().getId());
        st.setString(2, journalArticle.getTitle());
        st.setInt(3, journalArticle.getId());
        st.executeUpdate();
        database.getConnection().commit();

        query = "DELETE FROM author_of_article WHERE article_id = ?;";
        st = database.getConnection().prepareStatement(query);
        st.setInt(1, journalArticle.getId());
        st.executeUpdate();
        database.getConnection().commit();

        query = "INSERT INTO author_of_article VALUES(?, ?);";
        st = database.getConnection().prepareStatement(query);

        for (Author author : journalArticle.getAuthors()) {
            st.setInt(1, journalArticle.getId());
            st.setInt(2, author.getId());
            st.executeUpdate();
            database.getConnection().commit();
        }

        query = "DELETE FROM article_has_keyword WHERE article_id = ?;";
        st = database.getConnection().prepareStatement(query);
        st.setInt(1, journalArticle.getId());
        st.executeUpdate();
        database.getConnection().commit();

        query = "INSERT INTO article_has_keyword VALUES(?, ?);";
        st = database.getConnection().prepareStatement(query);

        for (Keyword word : journalArticle.getKeywords()) {
            st.setInt(1, journalArticle.getId());
            st.setInt(2, word.getId());
            st.executeUpdate();
            database.getConnection().commit();
        }
    }

    static private int getLastId() throws SQLException {
        String query = "SELECT LAST_INSERT_ID() FROM journal_articles;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        ResultSet rs = st.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
}
