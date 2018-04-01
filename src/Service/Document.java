package Service;

import DataBase.DocumentManager;

public class Document {
    private int id;
    private String title;
    private int value;
    String type;

    public Document(String title, int value, String type) {
        this.title = title;
        this.value = value;
        this.type = type;
    }

    public Document(int id, String title, int value, String type) {
        this.id = id;
        this.title = title;
        this.value = value;
        this.type = type;
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
}
