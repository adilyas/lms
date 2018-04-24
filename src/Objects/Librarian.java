package Objects;

public class Librarian extends User {

    public Librarian(String name, String surname, String type, String phoneNumber, String address, String email) {
        super(name, surname, type, phoneNumber, address, email);
    }

    public Librarian(int id, String name, String surname, String type, String phoneNumber, String address, String email) {
        super(id, name, surname, type, phoneNumber, address, email);
    }
}
