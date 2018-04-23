package Objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

public class Document {
    private int id;
    private String type;
    private String title;
    private int value;
    private boolean outstandingRequest;
    private Collection<Author> authors;
    private Collection<Keyword> keywords;
    private Collection<Patron> bookedBy;
    private Collection<Copy> copies;

    public Document(String type, String title, int value, boolean outstandingRequest) {
        this.type = type;
        this.title = title;
        this.value = value;
        this.outstandingRequest = outstandingRequest;
        this.authors = new ArrayList<>();
        this.keywords = new ArrayList<>();
        this.bookedBy = new ArrayList<>();
        this.copies = new ArrayList<>();
    }

    public Document(int id, String type, String title, int value, boolean outstandingRequest) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.value = value;
        this.outstandingRequest = outstandingRequest;
        this.authors = new ArrayList<>();
        this.keywords = new ArrayList<>();
        this.bookedBy = new ArrayList<>();
        this.copies = new ArrayList<>();
    }

    public boolean isOutstandingRequest() {
        return outstandingRequest;
    }

    public void setOutstandingRequest(boolean outstandingRequest) {
        this.outstandingRequest = outstandingRequest;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public String getTitle() {
        return title;
    }

    public Collection<Author> getAuthors() {
        return authors;
    }

    public Collection<Keyword> getKeywords() {
        return keywords;
    }

    public Collection<Patron> getBookedBy() {
        return bookedBy;
    }

    public Collection<Copy> getCopies() {
        return copies;
    }

    public Copy getFreeCopy() {
        for (Copy copy : copies) {
            if (!copy.isCheckedOut()) return copy;
        }
        return null;
    }

    public int quantityOfFreeCopies() {
        return (int) copies.stream()
                .filter(c -> !c.isCheckedOut())
                .count();
    }

}
