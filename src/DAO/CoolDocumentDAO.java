package DAO;

import Objects.Author;
import Objects.Book;
import Objects.Document;
import Objects.Keyword;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CoolDocumentDAO {

    static void insert(Document document) throws SQLException {
        if(document.getType() == "book"){
            BookDAO.insert((Book) document);
        }
    }
    this fucks dont work for now.
}
