package Objects;

import java.util.ArrayList;
import java.util.Collection;

public class Patron extends User {
    private Collection<Document> waitingList;
    private Collection<Copy> checkedOutCopies;

    public Patron(String name, String surname, String type, String phoneNumber, String address) {
        super(name, surname, type, phoneNumber, address);
        waitingList = new ArrayList<>();
        checkedOutCopies = new ArrayList<>();
    }

    public Patron(int id, String name, String surname, String phoneNumber, String address, String type) {
        super(id, name, surname, type, phoneNumber, address);
        waitingList = new ArrayList<>();
        checkedOutCopies = new ArrayList<>();
    }

    public Collection<Document> getWaitingList() {
        return waitingList;
    }

    public Collection<Copy> getCheckedOutCopies() {
        return checkedOutCopies;
    }
}
