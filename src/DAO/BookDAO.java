package DAO;

import Database.Database;
import Objects.Book;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class BookDAO {

    static private Database db;

    static void setDb(Database db){
        this.db = db;
    }

    static Book get(int ID) {
    }

    static void insert(Book book) throws SQLException {
        DocumentDAO.


        if (document.getType().toLowerCase().equals("book")) {
            final Book document1 = (Book) document;
            String bookAddQuery = "insert into books values(?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement stAddBook = connection.prepareStatement(bookAddQuery);
            stAddBook.setInt(1, documentId);
            stAddBook.setInt(2, (document1.getReference() ? 1 : 0));
            stAddBook.setInt(3, (document1.getBestSeller() ? 1 : 0));
            stAddBook.setString(4, document1.getPublisher());
            stAddBook.setDate(5,
                    java.sql.Date.valueOf(document1.getDateOfPublishingStr()));
            stAddBook.setInt(6, document1.getEdition());
            stAddBook.setInt(7, document1.getEditionYear());
            stAddBook.executeUpdate();
            connection.commit();
        }
    }

    void update (Book book){
    }
    void delete (Book book){
    }

    static private int getLastId() throws SQLException {
        String query = "select LAST_INSERT_ID() from ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setString(1, "books");
        ResultSet rs = st.executeQuery();
        rs.next();

        return rs.getInt(1);
    }
}
