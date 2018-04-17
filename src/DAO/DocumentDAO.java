package DAO;

import Database.Database;
import Objects.Author;
import Objects.Document;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class DocumentDAO {

    static private Database db;

    static void setDb(Database dbb){
        db = dbb;
    }

    static Document get(int ID) {
    }

    static void insert(Document document) throws SQLException {
        String docAddQuery = "insert into documents (title, value) " +
                "values (?, ?);";
        PreparedStatement st = db.getConnection().prepareStatement(docAddQuery);
        st.setString(1, document.getTitle());
        st.setInt(2, document.getValue());
        st.executeUpdate();
        db.getConnection().commit();
        document.setId(getLastId());

        linkAuthors(document.getAuthors(), document.getId());
        linkKeyWords(document.getKeywords(), document.getId());
    }

    static void update (Document document){
    }
    static void delete (Document document){
    }

    static private void linkKeyWords(Collection<String> keywords,
                              int documentId) throws SQLException {
        String addCon = "insert into document_has_keyword values(?, ?);";
        PreparedStatement addConSt = db.getConnection().prepareStatement(addCon);

        for (String word : keywords) {
            int wordId = addKeyWordIfNotExists(word);
            addConSt.setInt(1, documentId);
            addConSt.setInt(2, wordId);
            addConSt.executeUpdate();
            db.getConnection().commit();
        }
    }

    static private void linkAuthors(Collection<Author> authors,
                             int documentId) throws SQLException {

        String addCon = "insert into author_of_document values(?, ?);";
        PreparedStatement addConSt = db.getConnection().prepareStatement(addCon);
        for (int i = 0; i < authors.size(); i++) {

            Author author = (Author) (authors.get(i));
            if (author == null) {
                pm.addPerson((Author) authors.get(i));
                author = (Author) pm.find(authors.get(i));
            }

            if (!hasDocumentAuthor(documentId, author.getId())) {
                addConSt.setInt(1, documentId);
                addConSt.setInt(2, author.getId());
                addConSt.executeUpdate();
                db.getConnection().commit();
            }
        }
    }

    static private Boolean keywordsExists(String keyword) throws SQLException {
        String query = "select * from keywords where word = ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setString(1, keyword);

        ResultSet rs = st.executeQuery();

        return rs.next();
    }

    static private int addKeyWordIfNotExists(String keyword) throws SQLException {
        int wordId;
        if (!keywordsExists(keyword)) {
            String query = "insert into keywords (word) values(?);";
            PreparedStatement st = db.getConnection().prepareStatement(query);
            st.setString(1, keyword);
            st.executeUpdate();
            db.getConnection().commit();

            wordId = getLastId();
        } else {
            String getIdQuery = "select id from keywords where word = ?";
            PreparedStatement st = db.getConnection().prepareStatement(getIdQuery);
            st.setString(1, keyword);
            ResultSet rs = st.executeQuery();

            rs.next();

            wordId = rs.getInt("id");
        }

        return wordId;
    }

    static private int getLastId() throws SQLException {
        String query = "select LAST_INSERT_ID() from ?;";
        PreparedStatement st = db.getConnection().prepareStatement(query);
        st.setString(1, "documents");
        ResultSet rs = st.executeQuery();
        rs.next();

        return rs.getInt(1);
    }
}
