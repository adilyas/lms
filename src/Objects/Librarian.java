package Objects;

public class Librarian extends User {

    public Librarian(String name, String surname, String type, String phoneNumber, String address) {
        super(name, surname, type, phoneNumber, address);
    }

    public Librarian(int id, String name, String surname, String type, String phoneNumber, String address) {
        super(id, name, surname, type, phoneNumber, address);
    }
}
