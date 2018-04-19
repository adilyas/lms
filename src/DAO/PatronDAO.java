package DAO;

import Database.Database;
import Objects.Patron;
import Objects.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class PatronDAO {

    static private Database db;

    static void setDb(Database dbb) {
        db = dbb;
    }

    static void insert(Patron patron) throws SQLException {
        PersonDAO.insert(patron);

        String query = "INSERT INTO patrons (person_id) " +
                "VALUES (?);";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setInt(1, patron.getId());
        st.executeUpdate();
        db.getConnection().commit();
    }

    static void delete(Patron patron) throws SQLException {
        UserDAO.delete(patron);

        String query = "DELETE FROM patrons WHERE person_id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setInt(1, patron.getId());
        st.executeUpdate();
        db.getConnection().commit();

        query = "DELETE FROM patron_booked_document WHERE person_id = ?;";
        st = db.getConnection().prepareStatement(query);
        st.setInt(1, patron.getId());
        st.executeUpdate();
        db.getConnection().commit();
    }

    static Patron get(int id) throws SQLException {
        User user = UserDAO.get(id);

        String query = "SELECT 1 FROM users WHERE person_id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        db.getConnection().commit();
        Patron patron;
        if (rs.next()) {
            patron = new Patron(user.getId(), user.getName(), user.getSurname(), user.getType(), user.getPhoneNumber(),
                    user.getAddress());
        } else {
            throw new NoSuchElementException();
        }

        query = "SELECT d.id FROM documents d JOIN patron_booked_document pd ON d.id = pd.document_id " +
                "JOIN patrons p ON pd.person_id = p.person_id WHERE p.person_id = ?";
        st = db.getConnection().prepareStatement(query);
        st.setInt(1, patron.getId());
        rs = st.executeQuery();
        db.getConnection().commit();
        while (rs.next())
            patron.getWaitingList().add(CoolDocumentDAO.get(id));

        query = "SELECT id FROM copies WHERE holder_id = ?";
        st = db.getConnection().prepareStatement(query);
        st.setInt(1, patron.getId());
        rs = st.executeQuery();
        db.getConnection().commit();
        while (rs.next())
            patron.getWaitingList().add(CopyDAO.get(id));

        return patron;
    }

    static void update(Patron patron) throws SQLException {
        UserDAO.update(patron);
    }
}
