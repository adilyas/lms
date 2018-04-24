package Testcases;

import DAO.*;
import Database.Database;
import Objects.Author;
import Objects.JournalArticle;
import Objects.Librarian;
import Services.*;

import javax.naming.NoPermissionException;
import java.rmi.NoSuchObjectException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Testcase {
    private Database database;
    private LoggingService loggingService;
    private NotifyService notifyService;
    private BookingService bookingService;
    private LibrarianService librarianService;
    private PatronService patronService;
    private DocumentService documentService;

    public Testcase(Database database, LoggingService loggingService, NotifyService notifyService,
                    BookingService bookingService, LibrarianService librarianService, PatronService patronService,
                    DocumentService documentService) {
        this.database = database;
        this.bookingService = bookingService;
        this.loggingService = loggingService;
        this.notifyService = notifyService;
        this.librarianService = librarianService;
        this.patronService = patronService;
        this.documentService = documentService;
    }

    private void cleanTables() throws SQLException {
        String query = "DELETE FROM $table_name WHERE TRUE;";
        String[] tables = new String[]{"article_has_keyword", "author_of_article", "author_of_article",
                "author_of_document", "authors", "av_materials", "books", "copies", "document_has_keyword", "documents",
                "journal_articles", "journal_issues", "keywords", "librarians", "patron_booked_document", "patrons",
                "persons", "users"};
        for(String table: tables){
            String readyQuery = query.replace("$table_name", table);
            PreparedStatement statement = database.getConnection().prepareStatement(readyQuery);
            statement.executeUpdate();
        }
    }

    private void setDatabaseToDAO(){
        AuthorDAO.setDatabase(database);
        AVMaterialDAO.setDatabase(database);
        BookDAO.setDatabase(database);
        CopyDAO.setDatabase(database);
        DocumentDAO.setDatabase(database);
        JournalArticleDAO.setDatabase(database);
        JournalIssueDAO.setDatabase(database);
        KeywordDAO.setDatabase(database);
        LibrarianDAO.setDatabase(database);
        PatronDAO.setDatabase(database);
        PersonDAO.setDatabase(database);
        UserDAO.setDatabase(database);
    }

    public void testcase1() throws SQLException, NoPermissionException, NoSuchObjectException {
        cleanTables();
        setDatabaseToDAO();
        Librarian admin = new Librarian("Name", "Surname", "admin", "phone_number",
                "address", "email");
        LibrarianDAO.insert(admin);
        loggingService.logString("TESTCASE 1 START");
        librarianService.add(admin, new Librarian("Name1", "Surname1", "admin",
                "phone_number1","address1", "email1"));
        loggingService.logString("TESTCASE 1 FINISH");

    }
}
