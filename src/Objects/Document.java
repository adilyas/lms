package Objects;

import java.util.Collection;

public class Document {
    private int id;
    private String title;
    private int value;
    private Collection<Author> authors;
    private Collection<String> keywords;
    private String type;

    public Document(String title, int value, String type, Collection<Author> authors, Collection<String> keywords) {
        this.title = title;
        this.value = value;
        this.type = type;
        this.authors = authors;
        this.keywords = keywords;
    }

    public Document(int id, String title, int value, String type, Collection<Author> authors, Collection<String> keywords) {
        this.id = id;
        this.title = title;
        this.value = value;
        this.type = type;
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

    public String getType() {
        return type;
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

    public void setType(String type) {
        this.type = type;
    }

    public Collection<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Collection<Author> authors) {
        this.authors = authors;
    }

    public Collection<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Collection<String> keywords) {
        this.keywords = keywords;
    }
}
