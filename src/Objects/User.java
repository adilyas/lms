package Objects;

public class User extends Person {
    private String phoneNumber;
    private String address;

    public User(String name, String surname, String phoneNumber, String address) {
        super(name, surname);
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public User(int id, String name, String surname, String phoneNumber, String address) {
        super(id, name, surname);
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
