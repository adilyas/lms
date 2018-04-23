package DAO;

import Objects.AVMaterial;
import Objects.Book;
import Objects.Document;
import Objects.JournalIssue;

import java.sql.SQLException;
import java.util.NoSuchElementException;

public class CoolDocumentDAO {

    public static void insert(Document document) throws SQLException {
        switch (document.getType()) {
            case "book":
                BookDAO.insert((Book) document);
                break;
            case "av_material":
                AVMaterialDAO.insert((AVMaterial) document);
                break;
            case "journal_issue":
                JournalIssueDAO.insert((JournalIssue) document);
                break;
            default:
                throw new NoSuchElementException("Wrong type");
        }
    }

    static Document get(int id) throws SQLException {
        Document document = DocumentDAO.get(id);

        switch (document.getType()) {
            case "book":
                return BookDAO.get(id);
            case "av_material":
                return AVMaterialDAO.get(id);
            case "journal_issue":
                return JournalIssueDAO.get(id);
            default:
                throw new NoSuchElementException("Wrong type");
        }
    }

    public static void delete(Document document) throws SQLException {
        switch (document.getType()) {
            case "book":
                BookDAO.delete((Book) document);
                break;
            case "av_material":
                AVMaterialDAO.delete((AVMaterial) document);
                break;
            case "journal_issue":
                JournalIssueDAO.delete((JournalIssue) document);
                break;
            default:
                throw new NoSuchElementException("Wrong type");
        }
    }

    public static void update(Document document) throws SQLException {
        switch (document.getType()) {
            case "book":
                BookDAO.update((Book) document);
                break;
            case "av_material":
                AVMaterialDAO.update((AVMaterial) document);
                break;
            case "journal_issue":
                JournalIssueDAO.update((JournalIssue) document);
                break;
            default:
                throw new NoSuchElementException("Wrong type");
        }
    }
}
