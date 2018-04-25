package DAO;

import Database.Database;
import Objects.*;

import java.sql.*;
import java.util.NoSuchElementException;

public class CopyDAO {

    static private Database database;

    public static void setDatabase(Database database) {
        CopyDAO.database = database;
    }

    static void insert(Copy copy) throws SQLException {
        String query = "INSERT INTO copies (document_id, is_checked_out, holder_id, renew_times, check_out_date, due_date) " +
                "VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, copy.getDocument().getId());
        st.setBoolean(2, copy.isCheckedOut());
        if(copy.getHolder() == null){
            st.setNull(3, Types.INTEGER);
            st.setNull(5, Types.DATE);
            st.setNull(6, Types.DATE);
        } else{
            st.setInt(3, copy.getHolder().getId());
            st.setDate(5, Date.valueOf(copy.getCheckedOutDate()));
            st.setDate(6, Date.valueOf(copy.getDueDate()));
        }
        st.setInt(4, copy.getRenewTimes());
        st.executeUpdate();

        copy.setId(getLastId());
    }

    /**
     * Creates and inserts copy of document.
     * @param document
     * @throws SQLException
     */
    public static void insert(Document document) throws SQLException {
        Copy copy = new Copy(document);

        insert(copy);
    }

    public static void delete(Copy copy) throws SQLException {
        String query = "DELETE FROM copies WHERE id = ?";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, copy.getId());
        st.executeUpdate();

    }

    static Copy get(int id) throws SQLException {
        String query = "SELECT * FROM copies WHERE id = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();

        if (!rs.next())
            throw new NoSuchElementException();
        return new Copy(rs.getInt("id"), CoolDocumentDAO.get(rs.getInt("document_id")),
                PatronDAO.get(rs.getInt("holder_id")), rs.getBoolean("is_checked_out"),
                rs.getInt("renew_times"), rs.getDate("check_out_date").toLocalDate(),
                rs.getDate("due_date").toLocalDate());
    }

    static Copy get(int id, Document document) throws SQLException {
        String query = "SELECT * FROM copies WHERE id = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();

        if (!rs.next())
            throw new NoSuchElementException();
        return new Copy(rs.getInt("id"), document,
                (rs.getInt("holder_id")!=0?PatronDAO.get(rs.getInt("holder_id")):null),
                rs.getBoolean("is_checked_out"), rs.getInt("renew_times"),
                (rs.getDate("check_out_date")!=null?rs.getDate("check_out_date").toLocalDate():null),
                (rs.getDate("due_date")!=null?rs.getDate("due_date").toLocalDate():null));
    }

    public static void update(Copy copy) throws SQLException {
        String query = "UPDATE copies SET document_id = ?, is_checked_out = ?, holder_id = ?, renew_times = ?, " +
                "check_out_date = ?, due_date = ? WHERE id = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, copy.getDocument().getId());
        st.setBoolean(2, copy.isCheckedOut());
        if(copy.getHolder() == null){
            st.setNull(3, Types.INTEGER);
            st.setNull(5, Types.DATE);
            st.setNull(6, Types.DATE);
        } else{
            st.setInt(3, copy.getHolder().getId());
            st.setDate(5, Date.valueOf(copy.getCheckedOutDate()));
            st.setDate(6, Date.valueOf(copy.getDueDate()));
        }
        st.setInt(4, copy.getRenewTimes());
        st.setInt(7, copy.getId());
        st.executeUpdate();

    }

    static private int getLastId() throws SQLException {
        String query = "SELECT LAST_INSERT_ID() FROM copies;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        ResultSet rs = st.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
}
