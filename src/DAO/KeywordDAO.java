package DAO;

import Database.Database;
import Objects.Keyword;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class KeywordDAO {
    static private Database database;

    public static void setDatabase(Database database) {
        KeywordDAO.database = database;
    }

    static Keyword get(int id) throws SQLException {
        String query = "select * from keywords where id = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            return new Keyword(rs.getInt("id"), rs.getString("word"));
        } else {
            throw new NoSuchElementException();
        }
    }

    static Keyword getByWord(String word) throws SQLException {
        String query = "select * from keywords where word = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setString(1, word);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            return new Keyword(rs.getInt("id"), rs.getString("word"));
        } else {
            throw new NoSuchElementException();
        }
    }

    public static Keyword getByWordOrCreate(String word) throws SQLException {
        String query = "select * from keywords where word = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setString(1, word);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            return new Keyword(rs.getInt("id"), rs.getString("word"));
        } else {
            Keyword keyword = new Keyword(word);
            KeywordDAO.insert(keyword);
            return keyword;
        }
    }

    static void insert(Keyword keyword) throws SQLException {
        String query = "insert into keywords (word) " +
                "values (?);";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setString(1, keyword.getWord());
        st.executeUpdate();

        keyword.setId(KeywordDAO.getLastId());
    }

    static void update(Keyword keyword) throws SQLException {
        String query = "update keywords set " +
                "word = ? " +
                "where id = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setString(1, keyword.getWord());
        st.setInt(2, keyword.getId());
        st.executeUpdate();

    }

    static void delete(Keyword keyword) throws SQLException {
        String query = "delete from keywords where id = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, keyword.getId());
        st.executeUpdate();

    }

    static int getLastId() throws SQLException {
        String query = "select LAST_INSERT_ID() from keywords;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        ResultSet rs = st.executeQuery();
        rs.next();

        return rs.getInt(1);
    }
}
