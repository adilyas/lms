import Database.Database;
import Services.*;
import Testcases.Testcase;

import javax.naming.NoPermissionException;
import java.io.FileNotFoundException;
import java.rmi.NoSuchObjectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, NoPermissionException, SQLException, NoSuchObjectException {
        Database database = new Database();
        Connection connection = null;
        String userName = "remote_user";
        String password = "Pa$$w0rd";
        String connectionUrl = "jdbc:mysql://188.246.233.73:3306/lms";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(connectionUrl, userName, password);
            System.out.println("We're connected");
        } catch (Exception e) {
            System.out.println("Failed to connect to the database.");
            System.out.println(e.toString());
        }
        database.setConnection(connection);
        LoggingService loggingService = new LoggingService("logs.txt");
        NotifyService notifyService = new NotifyService();
        BookingService bookingService = new BookingService(database, loggingService, notifyService);
        LibrarianService librarianService = new LibrarianService(loggingService);
        PatronService patronService = new PatronService(loggingService);
        DocumentService documentService = new DocumentService(loggingService);
        try {
            Testcase testcase = new Testcase(database, loggingService, notifyService, bookingService, librarianService,
                    patronService, documentService);
            testcase.testcase5();
            loggingService.getPrintWriter().close();
        } catch (Exception e) {
            loggingService.getPrintWriter().close();
            throw e;
        }
        return;
    }
}
