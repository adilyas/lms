package Service;

public class Person {
    private int id;
    private String name;
    private String surname;
    private String type;

    public Person(String name, String surname, String type) {
        this.name = name;
        this.surname = surname;
        this.type = type;
    }

    public Person(int id, String name, String surname, String type) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setType(String type) {
        this.type = type;
    }
}
