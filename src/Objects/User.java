package Objects;

public class User extends Person {
    private String type;
    private String phoneNumber;
    private String address;
    private String email;

    public User(String name, String surname, String type, String phoneNumber, String address, String email) {
        super(name, surname);
        this.type = type;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
    }

    public User(int id, String name, String surname, String type, String phoneNumber, String address, String email) {
        super(id, name, surname);
        this.type = type;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return super.toString() + " type: " + this.type + " email: " + this.email;
    }
}
