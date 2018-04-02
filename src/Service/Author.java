package Service;

public class Author extends Person {

    public Author(int id, String name, String surname) {
        super(id, name, surname, "author");
    }

    public Author(String name, String surname) {
        super(name, surname, "author");
    }

    public static void add(Author author) {

    }
}
