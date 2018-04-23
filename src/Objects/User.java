package Objects;

public class User extends Person {
    private String type;
    private String phoneNumber;
    private String address;
    private String mailAddress;
    public User(String name, String surname, String type, String phoneNumber, String address) {
        super(name, surname);
        this.type = type;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public User(String name, String surname, String type, String phoneNumber, String address, String mailAddress) {
        super(name, surname);
        this.type = type;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.mailAddress = mailAddress;
    }

    public User(int id, String name, String surname, String type, String phoneNumber, String address) {
        super(id, name, surname);
        this.type = type;
        this.phoneNumber = phoneNumber;
        this.address = address;
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

    public String getMailAddress() {
        return mailAddress;
    }
}
