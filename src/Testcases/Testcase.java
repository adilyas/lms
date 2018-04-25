package Testcases;

import DAO.*;
import Database.Database;
import Objects.Author;
import Objects.Book;
import Objects.Librarian;
import Objects.Patron;
import Services.*;

import javax.naming.NoPermissionException;
import java.rmi.NoSuchObjectException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Testcase {
    private Database database;
    private LoggingService loggingService;
    private NotifyService notifyService;
    private BookingService bookingService;
    private LibrarianService librarianService;
    private PatronService patronService;
    private DocumentService documentService;
    private Librarian admin;

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
                "author_of_document", "document_has_keyword", "patron_booked_document", "authors", "av_materials",
                "books", "copies", "patrons", "librarians", "users", "documents", "persons"};
        for (String table : tables) {
            String readyQuery = query.replace("$table_name", table);
            PreparedStatement statement = database.getConnection().prepareStatement(readyQuery);
            statement.executeUpdate();
        }
    }

    private void setDatabaseToDAO() {
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

    /*
    Title: Introduction to Algorithms
    Authors: Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein Publisher: MIT Press
    Year: 2009
    Edition: Third edition
    Note: NA
    Price: 5000 rub.
    keywords: Algorithms, Data Structures, Complexity, Computational Theory
    d2
    Title: Algorithms + Data Structures = Programs Authors: Niklaus Wirth
    Publisher: Prentice Hall PTR
    Year: 1978
    Edition: First edition
    Note: NA
    Price: 5000 rub.
    keywords: Algorithms, Data Structures, Search Algorithms, Pascal
    d3
    Title: The Art of Computer Programming
    Authors: Donald E. Knuth
    Publisher: Addison Wesley Longman Publishing Co., Inc. Year: 1997
    Edition: Third edition
    Note: NA
    Price: 5000 rub.
    keywords: Algorithms, Combinatorial Algorithms, Recursion
    p1
    Name: Sergey Afonso Address: Via Margutta, 3 Phone Number: 30001 Lib. card ID: 1010
    Type: Faculty (Professor)
    p2
    Name: Nadia Teixeira Address: Via Sacra, 13 Phone Number: 30002 Lib. card ID: 1011
    Type: Faculty (Professor)
    p3
    Name: Elvira Espindola Address: Via del Corso, 22 Phone Number: 30003 Lib. card ID: 1100
    Type: Faculty (Professor)
    7
    s
    Name: Andrey Velo
    Address: Avenida Mazatlan 250 Phone Number: 30004
    Lib. card ID: 1101
    Type: Student
    v
    Name: Veronika Rama Address: Stret Atocha, 27 Phone Number: 30005 Lib. card ID: 1110
    Type: Visiting Professor
    l1
    Name: Eugenia Rama Privilege: Priv1
    l2
    Name: Luie Ramos Privilege: Priv2
    l3
    Name: Ramon Valdez Privilege: Priv3
     */

    public void init() throws SQLException {
        cleanTables();
        setDatabaseToDAO();
        admin = new Librarian("Name", "Surname", "admin", "phone_number",
                "address", "email");
        LibrarianDAO.insert(admin);
    }

    public void testcase1() throws SQLException, NoPermissionException, NoSuchObjectException {
        init();
        loggingService.logString("TESTCASE 1 START");
        librarianService.add(admin, new Librarian("Name0", "Surname0", "admin",
                "phone_numbe0", "address0", "email0"));
        loggingService.logString("TESTCASE 1 FINISH");

    }

    public void testcase2() throws SQLException, NoPermissionException, NoSuchObjectException {
        init();
        loggingService.logString("TESTCASE 2 START");
        librarianService.add(admin, new Librarian("Eugenia", "Rama", "librarian1",
                "phone_numbe1", "address1", "email1"));
        librarianService.add(admin, new Librarian("Luie", "Ramos", "librarian2",
                "phone_numbe2", "address2", "email2"));
        librarianService.add(admin, new Librarian("Ramon", "Valdez", "librarian3",
                "phone_numbe3", "address3", "email3"));
        loggingService.logString("TESTCASE 2 FINISH");

    }

    public void testcase3() throws SQLException, NoPermissionException, NoSuchObjectException {
        init();
        testcase2();
        loggingService.logString("TESTCASE 3 START");

        Book book1 = new Book("Introduction to Algorithms", 500,
                false, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                false, false, "MIT Press", LocalDate.of(2009, 1,
                1), 3, 2009);
        book1.getAuthors().add(AuthorDAO.getByNameOrCreate(new Author("Thomas H.", "Cormen")));
        book1.getAuthors().add(AuthorDAO.getByNameOrCreate(new Author("Charles E.", "Leiserson")));
        book1.getAuthors().add(AuthorDAO.getByNameOrCreate(new Author("Ronald L.", "Rivest")));
        book1.getAuthors().add(AuthorDAO.getByNameOrCreate(new Author("Clifford", "Stein")));
        book1.getKeywords().add(KeywordDAO.getByWordOrCreate("algorithms"));
        book1.getKeywords().add(KeywordDAO.getByWordOrCreate("data structures"));
        book1.getKeywords().add(KeywordDAO.getByWordOrCreate("complexity"));
        book1.getKeywords().add(KeywordDAO.getByWordOrCreate("computational theory"));
        documentService.add(LibrarianDAO.getByEmail("email1"), book1);
        documentService.addCopies(LibrarianDAO.getByEmail("email1"), book1, 3);

        Book book2 = new Book("Algorithms + Data Structures = Programs", 5000,
                false, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                false, false, "Prentice Hall PTR",
                LocalDate.of(1978, 1, 1), 1, 1978);
        book1.getAuthors().add(AuthorDAO.getByNameOrCreate(new Author("Niklaus", "Wirth")));
        book1.getKeywords().add(KeywordDAO.getByWordOrCreate("algorithms"));
        book1.getKeywords().add(KeywordDAO.getByWordOrCreate("data structures"));
        book1.getKeywords().add(KeywordDAO.getByWordOrCreate("search algorithms"));
        book1.getKeywords().add(KeywordDAO.getByWordOrCreate("Pascal"));
        documentService.add(LibrarianDAO.getByEmail("email1"), book2);
        documentService.addCopies(LibrarianDAO.getByEmail("email1"), book2, 3);

        Book book3 = new Book("The Art of Computer Programming", 5000,
                false, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                false, false, "Addison Wesley Longman Publishing Co., Inc.",
                LocalDate.of(1997, 1, 1), 1, 1997);
        book1.getAuthors().add(AuthorDAO.getByNameOrCreate(new Author("Donald E.", "Knuth")));
        book1.getKeywords().add(KeywordDAO.getByWordOrCreate("algorithms"));
        book1.getKeywords().add(KeywordDAO.getByWordOrCreate("recursion"));
        book1.getKeywords().add(KeywordDAO.getByWordOrCreate("combinatorial algorithms"));
        documentService.add(LibrarianDAO.getByEmail("email1"), book3);
        documentService.addCopies(LibrarianDAO.getByEmail("email1"), book3, 3);

        loggingService.logString("TESTCASE 3 FINISH");
    }

    public void testcase4() throws SQLException, NoPermissionException, NoSuchObjectException {
        init();
        testcase2();
        loggingService.logString("TESTCASE 4 START");
        Librarian l2 = LibrarianDAO.getByEmail("email2");

        Book book1 = new Book("Introduction to Algorithms", 500,
                false, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                false, false, "MIT Press", LocalDate.of(2009, 1,
                1), 3, 2009);
        book1.getAuthors().add(AuthorDAO.getByNameOrCreate(new Author("Thomas H.", "Cormen")));
        book1.getAuthors().add(AuthorDAO.getByNameOrCreate(new Author("Charles E.", "Leiserson")));
        book1.getAuthors().add(AuthorDAO.getByNameOrCreate(new Author("Ronald L.", "Rivest")));
        book1.getAuthors().add(AuthorDAO.getByNameOrCreate(new Author("Clifford", "Stein")));
        book1.getKeywords().add(KeywordDAO.getByWordOrCreate("algorithms"));
        book1.getKeywords().add(KeywordDAO.getByWordOrCreate("data structures"));
        book1.getKeywords().add(KeywordDAO.getByWordOrCreate("complexity"));
        book1.getKeywords().add(KeywordDAO.getByWordOrCreate("computational theory"));
        documentService.add(l2, book1);
        documentService.addCopies(l2, book1, 3);

        Book book2 = new Book("Algorithms + Data Structures = Programs", 5000,
                false, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                false, false, "Prentice Hall PTR",
                LocalDate.of(1978, 1, 1), 1, 1978);
        book1.getAuthors().add(AuthorDAO.getByNameOrCreate(new Author("Niklaus", "Wirth")));
        book1.getKeywords().add(KeywordDAO.getByWordOrCreate("algorithms"));
        book1.getKeywords().add(KeywordDAO.getByWordOrCreate("data structures"));
        book1.getKeywords().add(KeywordDAO.getByWordOrCreate("search algorithms"));
        book1.getKeywords().add(KeywordDAO.getByWordOrCreate("Pascal"));
        documentService.add(l2, book2);
        documentService.addCopies(l2, book2, 3);

        Book book3 = new Book("The Art of Computer Programming", 5000,
                false, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                false, false, "Addison Wesley Longman Publishing Co., Inc.",
                LocalDate.of(1997, 1, 1), 1, 1997);
        book1.getAuthors().add(AuthorDAO.getByNameOrCreate(new Author("Donald E.", "Knuth")));
        book1.getKeywords().add(KeywordDAO.getByWordOrCreate("algorithms"));
        book1.getKeywords().add(KeywordDAO.getByWordOrCreate("recursion"));
        book1.getKeywords().add(KeywordDAO.getByWordOrCreate("combinatorial algorithms"));
        documentService.add(l2, book3);
        documentService.addCopies(l2, book3, 3);


        Patron p1 = new Patron(1010, "Sergey", "Afonso", "professor", "30001",
                "Via Margutta, 3", "email4");
        Patron p2 = new Patron(1011, "Nadia", "Teixeira", "professor", "30002",
                "Via Sacra, 13", "email5");
        Patron p3 = new Patron(1100, "Elvira", "Espindola", "professor", "30003",
                "Via del Corso, 22", "email6");
        Patron s = new Patron(1101, "Andrey", "Velo", "student", "30004",
                "Avenida Mazatlan 250", "email7");
        Patron v = new Patron(1110, "Veronika", "Rama", "VP", "30005",
                "Stret Atocha, 27", "email8");
        patronService.add(l2, p1);
        patronService.add(l2, p2);
        patronService.add(l2, p3);
        patronService.add(l2, s);
        patronService.add(l2, v);

        loggingService.logString("TESTCASE 4 FINISH");
    }

    public void testcase5() throws SQLException, NoPermissionException, NoSuchObjectException {
        init();
        testcase4();
        loggingService.logString("TESTCASE 5 START");

        Librarian l3 = LibrarianDAO.getByEmail("email3");

//        Document d1 = DocumentDAO.get()
//        l3 =

    }
}
