package DAO;

import Database.Database;
import Objects.Book;
import Objects.Document;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public final class BookDAO {

    static private Database database;

    public static void setDatabase(Database database) {
        BookDAO.database = database;
    }

    static void insert(Book book) throws SQLException {
        DocumentDAO.insert(book);

        String query = "INSERT INTO books (document_id, is_reference, is_bestseller, publisher, date_of_publishing, " +
                "edition, edition_year) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, book.getId());
        st.setBoolean(2, book.getBestseller());
        st.setBoolean(3, book.getReference());
        st.setString(4, book.getPublisher());
        st.setDate(5, Date.valueOf(book.getDateOfPublishing()));
        st.setInt(6, book.getEdition());
        st.setInt(7, book.getEditionYear());
        st.executeUpdate();

    }

    static void delete(Book book) throws SQLException {
        String query = "DELETE FROM books WHERE document_id = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, book.getId());
        st.executeUpdate();


        DocumentDAO.delete(book);
    }

    static Book get(int id) throws SQLException {
        Document document = DocumentDAO.get(id);

        String query = "SELECT * FROM books WHERE document_id = ?";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        if (rs.next())
            return new Book(document.getId(), document.getTitle(), document.getValue(), document.isOutstandingRequest(),
                    document.getAuthors(), document.getKeywords(), document.getBookedBy(), document.getCopies(),
                    rs.getBoolean("is_reference"), rs.getBoolean("is_bestseller"),
                    rs.getString("Publisher"), rs.getDate("date_of_publishing").toLocalDate(),
                    rs.getInt("edition"), rs.getInt("editionYear"));
        else
            throw new NoSuchElementException();
    }

    static void update(Book book) throws SQLException {
        DocumentDAO.update(book);

        String query = "UPDATE books SET is_reference = ?, is_bestseller = ?, publisher = ?, date_of_publishing = ?, " +
                "edition = ?, edition_year = ? WHERE id = ?;";
        PreparedStatement st = database.getConnection().prepareStatement(query);
        st.setBoolean(1, book.getReference());
        st.setBoolean(2, book.getBestseller());
        st.setString(3, book.getPublisher());
        st.setDate(4, Date.valueOf(book.getDateOfPublishing()));
        st.setInt(5, book.getEdition());
        st.setInt(6, book.getEditionYear());
        st.setInt(7, book.getId());
        st.executeUpdate();

    }
}
