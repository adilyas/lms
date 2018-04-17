package DAO;

import Database.Database;
import Objects.Keyword;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class KeywordDAO {
    static private Database db;

    static void setDb(Database dbb) {
        db = dbb;
    }

    static Keyword get(int ID) throws SQLException {
        String query = "select * from keywords where id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setInt(1, ID);
        ResultSet rs = st.executeQuery();
        db.getConnection().commit();
        if (rs.next()) {
            return new Keyword(rs.getInt("id"), rs.getString("word"));
        } else {
            throw new NoSuchElementException();
        }
    }

    static Keyword getByWord(Keyword word) throws SQLException {
        String query = "select * from keywords where name = ? and surname = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setString(1, word.getWord());
        ResultSet rs = st.executeQuery();
        db.getConnection().commit();
        if (rs.next()) {
            return new Keyword(rs.getInt("id"), rs.getString("word"));
        } else {
            throw new NoSuchElementException();
        }
    }

    static void insert(Keyword keyword) throws SQLException {
        String query = "insert into keywords (word) " +
                "values (?);";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setString(1, keyword.getWord());
        st.executeUpdate();
        db.getConnection().commit();
        keyword.setId(KeywordDAO.getLastId());
    }

    static void update(Keyword keyword) throws SQLException {
        String query = "update keywords set" +
                "word = ? " +
                "where id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setString(1, keyword.getWord());
        st.setInt(2, keyword.getId());
        st.executeUpdate();
        db.getConnection().commit();
    }

    static void delete(Keyword keyword) throws SQLException {
        String query = "delete from keywords where id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setInt(1, keyword.getId());
        st.executeUpdate();
        db.getConnection().commit();
    }

    static int getLastId() throws SQLException {
        String query = "select LAST_INSERT_ID() from ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setString(1, "keywords");
        ResultSet rs = st.executeQuery();
        rs.next();

        return rs.getInt(1);
    }
}
