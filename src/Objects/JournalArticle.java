package Objects;

import java.util.Collection;

/**
 * WARNING
 * JournalIssue should be created before creation of JournalArticles of this issue.
 */
public class JournalArticle {
    private int id;
    private String title;
    private JournalIssue journalIssue;
    private Collection<Keyword> keywords;
    private Collection<Author> authors;

    public JournalArticle(String title, JournalIssue journalIssue, Collection<Author> authors, Collection<Keyword> keywords) {
        this.title = title;
        this.journalIssue = journalIssue;
        this.authors = authors;
        this.keywords = keywords;
    }

    public JournalArticle(int id, String title, JournalIssue journalIssue, Collection<Author> authors, Collection<Keyword> keywords) {
        this.id = id;
        this.title = title;
        this.journalIssue = journalIssue;
        this.authors = authors;
        this.keywords = keywords;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Collection<Keyword> getKeywords() {
        return keywords;
    }

    public Collection<Author> getAuthors() {
        return authors;
    }

    public JournalIssue getJournalIssue() {
        return journalIssue;
    }
}
