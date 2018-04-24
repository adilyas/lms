package Objects;

import java.time.LocalDate;
import java.util.Collection;

public class Book extends Document {
    private Boolean isReference;
    private Boolean isBestseller;
    private String publisher;
    private LocalDate dateOfPublishing;
    private int edition;
    private int editionYear;

    public Book(String title, int value, boolean outstandingRequest, Collection<Author> authors,
                Collection<Keyword> keywords, Collection<Patron> bookedBy, Collection<Copy> copies, Boolean isReference,
                Boolean isBestseller, String publisher, LocalDate dateOfPublishing, int edition, int editionYear) {
        super("book", title, value, outstandingRequest, authors, keywords, bookedBy, copies);
        this.isReference = isReference;
        this.isBestseller = isBestseller;
        this.publisher = publisher;
        this.dateOfPublishing = dateOfPublishing;
        this.edition = edition;
        this.editionYear = editionYear;
    }

    public Book(int id, String title, int value, boolean outstandingRequest, Collection<Author> authors,
                Collection<Keyword> keywords, Collection<Patron> bookedBy, Collection<Copy> copies, Boolean isReference,
                Boolean isBestseller, String publisher, LocalDate dateOfPublishing, int edition, int editionYear) {
        super(id, "book", title, value, outstandingRequest, authors, keywords, bookedBy, copies);
        this.isReference = isReference;
        this.isBestseller = isBestseller;
        this.publisher = publisher;
        this.dateOfPublishing = dateOfPublishing;
        this.edition = edition;
        this.editionYear = editionYear;
    }

    public Boolean getBestseller() {
        return isBestseller;
    }

    public Boolean getReference() {
        return isReference;
    }

    public int getEdition() {
        return edition;
    }

    public int getEditionYear() {
        return editionYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public LocalDate getDateOfPublishing() {
        return dateOfPublishing;
    }
}
