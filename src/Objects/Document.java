package Objects;

import java.util.ArrayList;
import java.util.Collection;

public class Document {
    private int id;
    private String type;
    private String title;
    private int value;
    private Collection<Author> authors;
    private Collection<Keyword> keywords;
    private Collection<Patron> bookedBy;

    public Document(String type, String title, int value) {
        this.type = type;
        this.title = title;
        this.value = value;
        this.authors = new ArrayList<>();
        this.keywords = new ArrayList<>();
        this.bookedBy = new ArrayList<>();
    }

    public Document(int id, String type, String title, int value) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.value = value;
        this.authors = new ArrayList<>();
        this.keywords = new ArrayList<>();
        this.bookedBy = new ArrayList<>();
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

}
