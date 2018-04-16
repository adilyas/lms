package Objects;

import java.time.LocalDate;

public class Book extends Document {
    private boolean isReference;
    private boolean isBestSeller;
    private String publisher;
    private LocalDate dateOfPublishing;
    private int edition;
    private int editionYear;

    public Book(int id, String title, int value,
                boolean isReference, boolean isBestSeller,
                String publisher, LocalDate dateOfPublishing,
                int edition, int editionYear) {
        super(id, title, value);
        this.dateOfPublishing = dateOfPublishing;
        this.edition = edition;
        this.editionYear = editionYear;
        this.isBestSeller = isBestSeller;
        this.isReference = isReference;
        this.publisher = publisher;
    }

    public Boolean getBestSeller() {
        return isBestSeller;
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

    public String getDateOfPublishingStr() {
        return dateOfPublishing.toString();
    }

    public void setBestSeller(Boolean bestSeller) {
        isBestSeller = bestSeller;
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
