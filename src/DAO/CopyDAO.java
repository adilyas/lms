package DAO;

import Database.Database;
import Objects.*;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class CopyDAO {

    static private Database database;

    static void setDatabase(Database database) {
        CopyDAO.database = database;
    }

    static void insert(Copy copy) throws SQLException {
        String query = "INSERT INTO copies (document_id, is_checked_out, holder_id, renew_times, check_out_date, due_date) " +
                "VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, copy.getDocument().getId());
        st.setBoolean(2, copy.isCheckedOut());
        st.setInt(3, copy.getHolder().getId());
        st.setInt(4, copy.getRenewTimes());
        st.setDate(5, Date.valueOf(copy.getCheckedOutDate()));
        st.setDate(6, Date.valueOf(copy.getDueDate()));
        st.executeUpdate();
        database.getConnection().commit();
        copy.setId(getLastId());
    }

    static void delete(Copy copy) throws SQLException {
        String query = "DELETE FROM copies WHERE id = ?";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, copy.getId());
        st.executeUpdate();
        database.getConnection().commit();
    }

    static Copy get(int id) throws SQLException {
        String query = "SELECT * FROM copies WHERE id = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        database.getConnection().commit();
        if (!rs.next())
            throw new NoSuchElementException();
        return new Copy(rs.getInt("id"), CoolDocumentDAO.get(rs.getInt("document_id")),
                PatronDAO.get(rs.getInt("holder_id")), rs.getBoolean("is_checked_out"),
                rs.getInt("renew_times"), rs.getDate("check_out_date").toLocalDate(),
                rs.getDate("due_date").toLocalDate());
    }

    public static void update(Copy copy) throws SQLException {
        String query = "UPDATE copies SET document_id = ?, is_checked_out = ?, holder_id = ?, renew_times = ?, " +
                "check_out_date = ?, due_date = ? WHERE id = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, copy.getDocument().getId());
        st.setBoolean(2, copy.isCheckedOut());
        st.setInt(3, copy.getHolder().getId());
        st.setInt(4, copy.getId());
        st.setDate(5, Date.valueOf(copy.getCheckedOutDate()));
        st.setDate(6, Date.valueOf(copy.getDueDate()));
        st.setInt(7, copy.getId());
        st.executeUpdate();
        database.getConnection().commit();
    }

    static private int getLastId() throws SQLException {
        String query = "SELECT LAST_INSERT_ID() FROM copies;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        ResultSet rs = st.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
}
