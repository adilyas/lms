package Services;

import DAO.CoolDocumentDAO;
import DAO.CopyDAO;
import Objects.Copy;
import Objects.Document;
import Objects.Librarian;

import javax.naming.NoPermissionException;
import java.sql.SQLException;

public class DocumentService {

    private LoggingService loggingService;

    public DocumentService(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    public void add(Librarian librarian, Document document) throws NoPermissionException, SQLException {
        loggingService.logString("ADD DOCUMENT START\n" +
                "LIBRARIAN " + librarian + "\n" +
                "DOCUMENT " + document);

        if (!librarian.getType().equals("librarian2") && !librarian.getType().equals("librarian3")) {
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

        if (!librarian.getType().equals("librarian3")) {
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

        if (!librarian.getType().equals("librarian1") && !librarian.getType().equals("librarian2") && !librarian.getType().equals("librarian3")) {
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


    public void addCopies(Librarian librarian, Document document, int quantity) throws NoPermissionException, SQLException {
        loggingService.logString("ADD COPIES START\n" +
                "LIBRARIAN " + librarian + "\n" +
                "DOCUMENT " + document + "\n" +
                "QUANTITY " + quantity);

        if (!librarian.getType().equals("librarian2") && !librarian.getType().equals("librarian3")) {
            loggingService.logString("ADD COPIES FINISH\n" +
                    "RESULT " + librarian.getType() + " privileges don't give right to add copies.");
            throw new NoPermissionException(librarian.getType() + " privileges don't give right to add copies.");
        }

        try {
            for (int i = 0; i < quantity; i++) {
                CopyDAO.insert(document);
            }
        } catch (SQLException e) {
            loggingService.logString("ADD COPIES FINISH\n" +
                    "RESULT " + "Something went wrong on data access layer.");
            throw e;
        }

        loggingService.logString("ADD COPIES FINISH\n" +
                "RESULT " + "Copies added.");
    }

    /**
     * Delete only free copies. Do nothing with checked out ones. If can't delete required amount of copies, delete as
     * many as possible.
     *
     * @param librarian
     * @param document
     * @throws NoPermissionException
     * @throws SQLException
     */
    public void deleteCopies(Librarian librarian, Document document, int quantity) throws NoPermissionException, SQLException {
        loggingService.logString("DELETE COPIES START\n" +
                "LIBRARIAN " + librarian + "\n" +
                "DOCUMENT " + document + "\n" +
                "QUANTITY " + quantity);

        if (!librarian.getType().equals("librarian3")) {
            loggingService.logString("DELETE COPIES FINISH\n" +
                    "RESULT " + librarian.getType() + " privileges don't give right to delete copies.");
            throw new NoPermissionException(librarian.getType() + " privileges don't give right to delete copies.");
        }

        int i = 0;
        try {
            for(i = 0; i < quantity; i++) {
                CopyDAO.delete(document.getFreeCopy());
            }
        } catch (SQLException e) {
            loggingService.logString("DELETE COPIES FINISH\n" +
                    "RESULT " + "Something went wrong on data access layer.");
            throw e;
        } catch (NullPointerException e){
            loggingService.logString("DELETE COPIES FINISH\n" +
                    "RESULT " + "There is no so many free copies in library. Deleted " + i + " copies.");
            throw e;
        }

        loggingService.logString("DELETE COPIES FINISH\n" +
                "RESULT " + "Copies deleted.");
    }
}
