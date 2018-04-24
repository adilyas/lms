package Objects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Authors of JournalIssue are editors of JournalIssue, actually. But for consistency of database they are called authors.
 */
public class JournalIssue extends Document {
    private String publisher;
    private LocalDate issueDate;
    private ArrayList<JournalArticle> journalArticles;

    public JournalIssue(String title, int value, boolean outstandingRequest, Collection<Author> authors,
                        Collection<Keyword> keywords, Collection<Patron> bookedBy, Collection<Copy> copies,
                        String publisher, LocalDate issueDate) {
        super("journal_issue", title, value, outstandingRequest, authors, keywords, bookedBy, copies);
        this.publisher = publisher;
        this.issueDate = issueDate;
        this.journalArticles = new ArrayList<>();
    }

    public JournalIssue(int id, String title, int value, boolean outstandingRequest, Collection<Author> authors,
                        Collection<Keyword> keywords, Collection<Patron> bookedBy, Collection<Copy> copies,
                        String publisher, LocalDate issueDate) {
        super(id, "journal_issue", title, value, outstandingRequest, authors, keywords, bookedBy, copies);
        this.publisher = publisher;
        this.issueDate = issueDate;
        this.journalArticles = new ArrayList<>();
    }

    public ArrayList<JournalArticle> getJournalArticles() {
        return journalArticles;
    }

    public String getPublisher() {
        return publisher;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }
}
