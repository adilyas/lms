package Services;

import Database.Database;
import Objects.Copy;
import Objects.Patron;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReturnService {
    static private Database db;

    static void setDb(Database database) {
        db = database;
    }

    /**
     * Implements return logic.
     *
     * @param patron patron who returns the copy.
     * @param copy copy which is going to be returned.
     * @throws SQLException
     */
    public static void return_(Patron patron, Copy copy) throws SQLException {
        String updateText = "update copies set is_checked_out = 0, holder_id = NULL, renew_times = 0, check_out_date = NULL, due_date = NULL where id = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(updateText);

        st.setInt(1, copy.getId());
        st.executeUpdate();
        db.getConnection().commit();
    }
}
