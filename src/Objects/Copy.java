package Objects;

import java.time.LocalDate;

public class Copy {
    private int id;
    private Document document;
    private Patron holder;
    private boolean isCheckedOut;

    public void setHolder(Patron holder) {
        this.holder = holder;
    }

    public void setCheckedOut(boolean checkedOut) {
        isCheckedOut = checkedOut;
    }

    public void setRenewTimes(int renewTimes) {
        this.renewTimes = renewTimes;
    }

    public void setCheckedOutDate(LocalDate checkedOutDate) {
        CheckedOutDate = checkedOutDate;
    }

    public void setDueDate(LocalDate dueDate) {
        DueDate = dueDate;
    }

    private int renewTimes;
    private LocalDate CheckedOutDate;
    private LocalDate DueDate;
    public Copy(Document document) {
        this.document = document;
        this.holder = null;
        this.isCheckedOut = false;
        this.renewTimes = 0;
        CheckedOutDate = null;
        DueDate = null;
    }

    public Copy(Document document, Patron holder, boolean isCheckedOut, int renewTimes, LocalDate checkedOutDate, LocalDate dueDate) {
        this.document = document;
        this.holder = holder;
        this.isCheckedOut = isCheckedOut;
        this.renewTimes = renewTimes;
        CheckedOutDate = checkedOutDate;
        DueDate = dueDate;
    }

    public Copy(int id, Document document, Patron holder, boolean isCheckedOut, int renewTimes, LocalDate checkedOutDate, LocalDate dueDate) {
        this.id = id;
        this.document = document;
        this.holder = holder;
        this.isCheckedOut = isCheckedOut;
        this.renewTimes = renewTimes;
        CheckedOutDate = checkedOutDate;
        DueDate = dueDate;
    }

    public Patron getHolder() {
        return holder;
    }

    public int getId() {
        return id;
    }

    public Document getDocument() {
        return document;
    }

    public boolean isCheckedOut() {
        return isCheckedOut;
    }

    public int getRenewTimes() {
        return renewTimes;
    }

    public LocalDate getCheckedOutDate() {
        return CheckedOutDate;
    }

    public LocalDate getDueDate() {
        return DueDate;
    }

    public void setId(int id) {
        this.id = id;
    }
}
