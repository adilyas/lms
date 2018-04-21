package Services;

import Database.Database;
import Objects.Copy;
import Objects.Patron;

import java.sql.SQLException;

public class ReturnService {
    static private Database db;

    static void setDb(Database dbb) {
        db = dbb;
    }

    public static void return_(Patron patron, Copy copy) throws SQLException {

    }
}
