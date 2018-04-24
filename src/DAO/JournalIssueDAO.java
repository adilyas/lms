package DAO;

import Database.Database;
import Objects.*;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class JournalIssueDAO {

    static private Database database;

    public static void setDatabase(Database database) {
        JournalIssueDAO.database = database;
    }

    static void insert(JournalIssue journalIssue) throws SQLException {
        DocumentDAO.insert(journalIssue);

        String query = "INSERT INTO journal_issues (document_id, publisher, issue_date) " +
                "VALUES (?, ?, ?);";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, journalIssue.getId());
        st.setString(2, journalIssue.getPublisher());
        st.setDate(3, Date.valueOf(journalIssue.getIssueDate()));
        st.executeUpdate();
        database.getConnection().commit();
        journalIssue.setId(getLastId());
    }

    static void delete(JournalIssue journalIssue) throws SQLException {
        String query = "DELETE FROM journal_issues WHERE id = ?";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, journalIssue.getId());
        st.executeUpdate();
        database.getConnection().commit();

        DocumentDAO.delete(journalIssue);
    }

    static JournalIssue get(int id) throws SQLException {
        Document document = DocumentDAO.get(id);

        String query = "SELECT * FROM journal_issues WHERE document_id = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        database.getConnection().commit();
        if (!rs.next())
            throw new NoSuchElementException();
        JournalIssue journalIssue = new JournalIssue(document.getId(), document.getTitle(), document.getValue(),
                document.getAuthors(), document.getKeywords(), rs.getString("publisher"),
                rs.getDate("issue_date").toLocalDate());

        query = "SELECT id FROM journal_articles WHERE issue_id = ?";
        st = database.getConnection().prepareStatement(query);
        st.setInt(1, journalIssue.getId());
        rs = st.executeQuery();
        database.getConnection().commit();
        while (rs.next())
            journalIssue.getJournalArticles().add(JournalArticleDAO.get(rs.getInt("id")));
        return journalIssue;
    }

    static void update(JournalIssue journalIssue) throws SQLException {
        DocumentDAO.update(journalIssue);

        String query = "UPDATE journal_issues SET publisher = ?, issue_date = ? WHERE id = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setString(1, journalIssue.getPublisher());
        st.setDate(2, Date.valueOf(journalIssue.getIssueDate()));
        st.setInt(3, journalIssue.getId());
        st.executeUpdate();
        database.getConnection().commit();
    }

    static private int getLastId() throws SQLException {
        String query = "SELECT LAST_INSERT_ID() FROM journal_articles;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        ResultSet rs = st.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
}
