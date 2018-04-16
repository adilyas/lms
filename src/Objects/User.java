package Objects;

public class User extends Person {
    private String phoneNumber;
    String type;

    public User(String name, String surname, String phoneNumber, String type) {
        super(name, surname, type);
        this.phoneNumber = phoneNumber;
        this.type = type;
    }

    public User(int id, String name, String surname,
                String phoneNumber, String type) {
        super(id, name, surname, type);
        this.phoneNumber = phoneNumber;
        this.type = type;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getType() {
        return type;
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getSurname() {
        return super.getSurname();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void setId(int id) {
        super.setId(id);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public void setSurname(String surname) {
        super.setSurname(surname);
    }
}
