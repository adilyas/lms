package Objects;

import java.security.Key;
import java.util.Collection;

public class Document {
    private int id;
    private String title;
    private int value;
    private Collection<Author> authors;
    private Collection<Keyword> keywords;
    public Document(String title, int value, Collection<Author> authors, Collection<Keyword> keywords) {
        this.title = title;
        this.value = value;
        this.authors = authors;
        this.keywords = keywords;
    }

    public Document(int id, String title, int value, Collection<Author> authors, Collection<Keyword> keywords) {
        this.id = id;
        this.title = title;
        this.value = value;
        this.authors = authors;
        this.keywords = keywords;
    }

    public int getId() {
        return id;
    }

    public int getValue() {
        return value;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Collection<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Collection<Author> authors) {
        this.authors = authors;
    }

    public Collection<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(Collection<Keyword> keywords) {
        this.keywords = keywords;
    }
}
