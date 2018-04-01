package DataBase;

import Service.Author;
import Service.Book;
import Service.Document;
import Service.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DocumentManager extends Database {
    PersonManager pm;

    public void insert(Document document,
                       ArrayList<Person> authors,
                       ArrayList<String> keywords) throws Exception {
        String docAddQuery = "insert into documents (title, value) " +
                "values (?, ?);";
        PreparedStatement st = connection.prepareStatement(docAddQuery);
        st.setString(1, document.getTitle());
        st.setInt(2, document.getValue());
        st.executeUpdate();
        connection.commit();

        int documentId = getLastId("documents");

        linkAuthors(authors, documentId);
        linkKeyWords(keywords, documentId);


        if (document instanceof Book) {
            final Book document1 = (Book) document;
            String bookAddQuery = "insert into books values(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement st = connection.prepareStatement(bookAddQuery);
            st.setInt(1, documentId);
            st.setInt(2, (document1.getReference() ? 1 : 0));
            st.setInt(3, (document1.getBestSeller() ? 1 : 0));
            st.setString(4, document1.getPublisher());
            st.setDate(5, java.sql.Date.valueOf(document1.getDateOfPublishingStr()));
            st.setInt(6, document1.getEdition());
            st.setInt(7, document1.getEditionYear());
            st.executeUpdate();
            connection.commit();
        }
    }

    public Document find() {

    }

    private Boolean keywordsExists(String keyword) throws Exception {
        String query = "select * from keywords where word = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setString(1, keyword);

        ResultSet rs = st.executeQuery();

        return rs.next();
    }

    private int addKeyWordIfNotExists(String keyword) throws Exception {
        int wordId;
        if (!keywordsExists()) {
            String query = "insert into keywords (word) values(?);";
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(keyword);
            st.executeUpdate();
            connection.commit();

            wordId = getLastId("keywords");
        } else {
            String getIdQuery = "select id from keywords where word = ?";
            PreparedStatement st = connection.prepareStatement(getIdQuery);
            st.setString(1, keyword);
            ResultSet rs = st.executeQuery();

            rs.next();

            wordId = rs.getInt("id");
        }

        return wordId;
    }

    private Boolean hasDocumentKeyword(int documentId,
                                       int keywordId) throws Exception {
        String query = "select from document_has_keyword as d " +
                "where d.document_id = ? and d.keyword_id = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1, documentId);
        st.setInt(2, keywordId);

        ResultSet rs = st.executeQuery();
        return rs.next();
    }

    private void linkKeyWords(ArrayList<String> keywords,
                              int documentId) throws Exception {
        String addCon = "insert into document_has_keyword values(?, ?);";
        PreparedStatement addConSt = connection.prepareStatement(addCon);

        for (String word : keywords) {
            int wordId = addKeyWordIfNotExists(word);

            if (!hasDocumentKeyword(documentId, wordId)) {
                addConSt.setInt(1, documentId);
                addConSt.setInt(2, wordId);
                addConSt.executeUpdate();
                connection.commit();
            }
        }
    }

    public Boolean hasDocumentAuthor(int documentId,
                                     int personId) throws Exception {
        String query = "select from author_of_document as a " +
                "where a.document_id = ? and a.author_id = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1, documentId);
        st.setInt(2, personId);

        ResultSet rs = st.executeQuery();
        return rs.next();
    }

    private void linkAuthors(ArrayList<Person> authors,
                             int documentId) throws Exception {

        String addCon = "insert into author_of_document values(?, ?);";
        PreparedStatement addConSt = connection.prepareStatement(addCon);
        for (int i = 0; i < authors.size(); i++) {

            Author author = (Author) pm.find(authors.get(i));
            if (author == null) {
                pm.addPerson((Author) authors.get(i));
                author = (Author) pm.find(authors.get(i));
            }

            if (!hasDocumentAuthor(documentId, author.getId())) {
                addConSt.setInt(1, documentId);
                addConSt.setInt(2, author.getId());
                addConSt.executeUpdate();
                connection.commit();
            }
        }
    }

    private int getLastId(String from) throws Exception {
        String query = "select max(id) from ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setString(1, from);
        ResultSet rs = st.executeQuery();
        rs.next();

        return rs.getInt("max(id)");
    }
}
