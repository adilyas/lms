package Services;

import DAO.PatronDAO;
import Database.Database;
import Objects.Librarian;
import Objects.Patron;

import javax.naming.NoPermissionException;
import java.sql.SQLException;

public class PatronService {

    private Database database;
    private LoggingService loggingService;

    public PatronService(Database database, LoggingService loggingService) {
        this.database = database;
        this.loggingService = loggingService;
    }

    public void add(Librarian librarian, Patron patron) throws NoPermissionException, SQLException {
        loggingService.logString("ADD PATRON START\n" +
                "LIBRARIAN " + librarian + "\n" +
                "PATRON " + patron);

        if (!librarian.getType().equals("librarian2") && !librarian.getType().equals("librarian3")) {
            loggingService.logString("ADD PATRON FINISH\n" +
                    "RESULT " + librarian.getType() + " privileges don't give right to add patrons.");
            throw new NoPermissionException(librarian.getType() + " privileges don't give right to add patrons.");
        }

        try {
            PatronDAO.insert(patron);
        } catch (SQLException e) {
            loggingService.logString("ADD PATRON FINISH\n" +
                    "RESULT " + "Something went wrong on data access layer.");
            throw e;
        }

        loggingService.logString("ADD PATRON FINISH\n" +
                "RESULT " + "Patron added.");
    }

    public void delete(Librarian librarian, Patron patron) throws NoPermissionException, SQLException {
        loggingService.logString("DELETE PATRON START\n" +
                "LIBRARIAN " + librarian + "\n" +
                "PATRON " + patron);

        if (!librarian.getType().equals("librarian3")) {
            loggingService.logString("DELETE PATRON FINISH\n" +
                    "RESULT " + librarian.getType() + " privileges don't give right to delete patrons.");
            throw new NoPermissionException(librarian.getType() + " privileges don't give right to delete patrons.");
        }

        try {
            PatronDAO.delete(patron);
        } catch (SQLException e) {
            loggingService.logString("DELETE PATRON FINISH\n" +
                    "RESULT " + "Something went wrong on data access layer.");
            throw e;
        }

        loggingService.logString("DELETE PATRON FINISH\n" +
                "RESULT " + "Patron deleted.");
    }

    public void modify(Librarian librarian, Patron patron) throws NoPermissionException, SQLException {
        loggingService.logString("MODIFY PATRON START\n" +
                "LIBRARIAN " + librarian + "\n" +
                "PATRON " + patron);

        if (!librarian.getType().equals("librarian2") && !librarian.getType().equals("librarian3")) {
            loggingService.logString("MODIFY PATRON FINISH\n" +
                    "RESULT " + librarian.getType() + " privileges don't give right to modify patrons.");
            throw new NoPermissionException(librarian.getType() + " privileges don't give right to modify patrons.");
        }

        try {
            PatronDAO.update(patron);
        } catch (SQLException e) {
            loggingService.logString("MODIFY PATRON FINISH\n" +
                    "RESULT " + "Something went wrong on data access layer.");
            throw e;
        }

        loggingService.logString("MODIFY PATRON FINISH\n" +
                "RESULT " + "Patron modify.");
    }
}
