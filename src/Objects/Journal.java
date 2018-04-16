package Objects;

import java.time.LocalDate;

public class Journal extends Document {
    private Boolean isReference;
    private String publisher;
    private LocalDate issue;

    public Journal(int id, String title, int value, Boolean isReference, String publisher, LocalDate issue) {
        super(id, title, value);
        this.isReference = isReference;
        this.publisher = publisher;
        this.issue = issue;
    }

    public String getPublisher() {
        return publisher;
    }

    public Boolean getReference() {
        return isReference;
    }

    public LocalDate getIssue() {
        return issue;
    }

    public String getIssueStr() {
        return issue.toString();
    }

    public void setReference(Boolean reference) {
        isReference = reference;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setIssue(LocalDate issue) {
        this.issue = issue;
    }
}
