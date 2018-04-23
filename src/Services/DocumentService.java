package Services;

import DAO.CoolDocumentDAO;
import Database.Database;
import Objects.Document;
import Objects.Librarian;

import javax.naming.NoPermissionException;
import java.sql.SQLException;

public class DocumentService {

    private Database database;
    private LoggingService loggingService;

    public DocumentService(Database database, LoggingService loggingService) {
        this.database = database;
        this.loggingService = loggingService;
    }

    public void add(Librarian librarian, Document document) throws NoPermissionException, SQLException {
        loggingService.logString("ADD DOCUMENT START\n" +
                "LIBRARIAN " + librarian + "\n" +
                "DOCUMENT " + document);

        if(!librarian.getType().equals("librarian2") && !librarian.getType().equals("librarian3")){
            loggingService.logString("ADD DOCUMENT FINISH\n" +
                    "RESULT " + librarian.getType() + " privileges don't give right to add documents.");
            throw new NoPermissionException(librarian.getType() + " privileges don't give right to add documents.");
        }

        try {
            CoolDocumentDAO.insert(document);
        } catch (SQLException e) {
            loggingService.logString("ADD DOCUMENT FINISH\n" +
                    "RESULT " + "Something went wrong on data access layer.");
            throw e;
        }

        loggingService.logString("ADD DOCUMENT FINISH\n" +
                "RESULT " + "Document added.");
    }

    public void delete(Librarian librarian, Document document) throws NoPermissionException, SQLException {
        loggingService.logString("DELETE DOCUMENT START\n" +
                "LIBRARIAN " + librarian + "\n" +
                "DOCUMENT " + document);

        if(!librarian.getType().equals("librarian3")){
            loggingService.logString("DELETE DOCUMENT FINISH\n" +
                    "RESULT " + librarian.getType() + " privileges don't give right to delete documents.");
            throw new NoPermissionException(librarian.getType() + " privileges don't give right to delete documents.");
        }

        try {
            CoolDocumentDAO.delete(document);
        } catch (SQLException e) {
            loggingService.logString("DELETE DOCUMENT FINISH\n" +
                    "RESULT " + "Something went wrong on data access layer.");
            throw e;
        }

        loggingService.logString("DELETE DOCUMENT FINISH\n" +
                "RESULT " + "Document deleted.");
    }

    public void modify(Librarian librarian, Document document) throws NoPermissionException, SQLException {
        loggingService.logString("MODIFY DOCUMENT START\n" +
                "LIBRARIAN " + librarian + "\n" +
                "DOCUMENT " + document);

        if(!librarian.getType().equals("librarian1") && !librarian.getType().equals("librarian2") && !librarian.getType().equals("librarian3")){
            loggingService.logString("MODIFY DOCUMENT FINISH\n" +
                    "RESULT " + librarian.getType() + " privileges don't give right to modify documents.");
            throw new NoPermissionException(librarian.getType() + " privileges don't give right to modify documents.");
        }

        try {
            CoolDocumentDAO.update(document);
        } catch (SQLException e) {
            loggingService.logString("MODIFY DOCUMENT FINISH\n" +
                    "RESULT " + "Something went wrong on data access layer.");
            throw e;
        }

        loggingService.logString("MODIFY DOCUMENT FINISH\n" +
                "RESULT " + "Document modified.");
    }
}
