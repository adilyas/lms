package Objects;

public class Author extends Person {

    public Author(Integer id, String name, String surname) {
        super(id, name, surname);
    }

    public Author(String name, String surname) {
        super(name, surname);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
