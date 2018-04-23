package Services;

import DAO.LibrarianDAO;
import Objects.Librarian;

import javax.naming.NoPermissionException;
import java.rmi.NoSuchObjectException;
import java.sql.SQLException;

public class LibrarianService {

    private LoggingService loggingService;

    public LibrarianService(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    public void add(Librarian admin, Librarian librarian) throws NoPermissionException, NoSuchObjectException {
        loggingService.logString("ADD LIBRARIAN START\n" +
                "ADMIN " + admin + "\n" +
                "LIBRARIAN " + librarian);

        if (!admin.getType().equals("admin")) {
            loggingService.logString("ADD LIBRARIAN FINISH\n" +
                    "RESULT " + librarian.getType() + " privileges don't give right to add librarians.");
            throw new NoPermissionException(librarian.getType() + " privileges don't give right to add librarians.");
        }

        if (isPossibleType(librarian)) {
            loggingService.logString("ADD LIBRARIAN FINISH\n" +
                    "RESULT " + "Librarian of type " + librarian.getType() + " can't be added to library.");
            throw new NoSuchObjectException("Librarian of type " + librarian.getType() + " can't be added to library.");
        }

        try {
            LibrarianDAO.insert(librarian);
        } catch (SQLException e) {
            loggingService.logString("ADD LIBRARIAN FINISH\n" +
                    "RESULT " + "Something went wrong on data access layer.");
            e.printStackTrace();
        }

        loggingService.logString("ADD LIBRARIAN FINISH\n" +
                "RESULT " + "Librarian added.");
    }


    public void delete(Librarian admin, Librarian librarian) throws NoPermissionException {
        loggingService.logString("DELETE LIBRARIAN START\n" +
                "ADMIN " + admin + "\n" +
                "LIBRARIAN " + librarian);

        if (!admin.getType().equals("admin")) {
            loggingService.logString("ADD LIBRARIAN FINISH\n" +
                    "RESULT " + librarian.getType() + " privileges don't give right to delete librarians.");
            throw new NoPermissionException(librarian.getType() + " privileges don't give right to delete librarians.");
        }

        try {
            LibrarianDAO.insert(librarian);
        } catch (SQLException e) {
            loggingService.logString("DELETE LIBRARIAN FINISH\n" +
                    "RESULT " + "Something went wrong on data access layer.");
            e.printStackTrace();
        }

        loggingService.logString("DELETE LIBRARIAN FINISH\n" +
                "RESULT " + "Librarian deleted.");
    }


    public void modify(Librarian admin, Librarian librarian) throws NoSuchObjectException, NoPermissionException {
        loggingService.logString("MODIFY LIBRARIAN START\n" +
                "ADMIN " + admin + "\n" +
                "LIBRARIAN " + librarian);

        if (!admin.getType().equals("admin")) {
            loggingService.logString("ADD LIBRARIAN FINISH\n" +
                    "RESULT " + librarian.getType() + " privileges don't give right to modify librarians.");
            throw new NoPermissionException(librarian.getType() + " privileges don't give right to modify librarians.");
        }

        if (isPossibleType(librarian)) {
            loggingService.logString("ADD LIBRARIAN FINISH\n" +
                    "RESULT " + "Librarian of type " + librarian.getType() + " can't be in library.");
            throw new NoSuchObjectException("Librarian of type " + librarian.getType() + " can't be in library.");
        }

        try {
            LibrarianDAO.update(librarian);
        } catch (SQLException e) {
            loggingService.logString("MODIFY LIBRARIAN FINISH\n" +
                    "RESULT " + "Something went wrong on data access layer.");
            e.printStackTrace();
        }

        loggingService.logString("MODIFY LIBRARIAN FINISH\n" +
                "RESULT " + "Librarian modified.");
    }

    private boolean isPossibleType(Librarian librarian) {
        return librarian.getType().equals("librarian1") || librarian.getType().equals("librarian2") || librarian.getType().equals("librarian3");
    }
}
