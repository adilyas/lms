import Database.Database;
import Services.*;
import Testcases.Testcase;

import javax.naming.NoPermissionException;
import java.io.FileNotFoundException;
import java.rmi.NoSuchObjectException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, NoPermissionException, SQLException, NoSuchObjectException {
        Database database = new Database();
        database.setConnection(new Connection());
        LoggingService loggingService = new LoggingService("logs.txt");
        NotifyService notifyService = new NotifyService();
        BookingService bookingService = new BookingService(database, loggingService, notifyService);
        LibrarianService librarianService = new LibrarianService(loggingService);
        PatronService patronService = new PatronService(loggingService);
        DocumentService documentService = new DocumentService(loggingService);

        Testcase testcase = new Testcase(database, loggingService, notifyService, bookingService, librarianService,
                patronService, documentService);
        testcase.testcase1();
    }
}
