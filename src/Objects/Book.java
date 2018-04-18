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

    public Book(String title, int value, Collection<Author> authors, Collection<Keyword> keywords, Boolean isReference,
                Boolean isBestseller, String publisher, LocalDate dateOfPublishing, int edition, int editionYear) {
        super(title, value, authors, keywords);
        this.isReference = isReference;
        this.isBestseller = isBestseller;
        this.publisher = publisher;
        this.dateOfPublishing = dateOfPublishing;
        this.edition = edition;
        this.editionYear = editionYear;
    }

    /**
     *
     * @param id id
     * @param title title
     * @param value value
     * @param authors authors
     * @param keywords keywords
     * @param isReference isReference
     * @param isBestseller isBestseller
     * @param publisher publisher
     * @param dateOfPublishing dateOfPublishing
     * @param edition edition
     * @param editionYear editionsYear
     */
    public Book(int id, String title, int value, Collection<Author> authors, Collection<Keyword> keywords,
                Boolean isReference, Boolean isBestseller, String publisher, LocalDate dateOfPublishing, int edition,
                int editionYear) {
        super(id, title, value, authors, keywords);
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

    public void setBestseller(Boolean bestSeller) {
        isBestseller = bestSeller;
    }

    public void setDateOfPublishing(LocalDate dateOfPublishing) {
        this.dateOfPublishing = dateOfPublishing;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public void setEditionYear(int editionYear) {
        this.editionYear = editionYear;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setReference(Boolean reference) {
        isReference = reference;
    }
}
