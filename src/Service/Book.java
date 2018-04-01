package Service;

import DataBase.DocumentManager;

import java.time.LocalDate;

public class Book extends Document {
    private Boolean isReference;
    private Boolean isBestSeller;
    private String publisher;
    private LocalDate dateOfPublishing;
    private int edition;
    private int editionYear;

    public Book(String title, int value,
                Boolean isReference, Boolean isBestSeller,
                String publisher, LocalDate dateOfPublishing,
                int edition, int editionYear) {
        super(title, value, "book");
        this.isReference = isReference;
        this.isBestSeller = isBestSeller;
        this.publisher = publisher;
        this.dateOfPublishing = dateOfPublishing;
        this.edition = edition;
        this.editionYear = editionYear;
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public int getValue() {
        return super.getValue();
    }

    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @Override
    public String getType() {
        return super.getType();
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

    @Override
    public void setId(int id) {
        super.setId(id);
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
    }

    @Override
    public void setType(String type) {
        super.setType(type);
    }

    @Override
    public void setValue(int value) {
        super.setValue(value);
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

    public static void add(DocumentManager dm,
                           String title, int value,
                           Boolean isReference, Boolean isBestSeller,
                           String publisher, LocalDate dateOfPublishing,
                           int edition, int editionYear) throws Exception {

    }

    public static void retrieveAll(DocumentManager dm) throws Exception {

    }

    public static void update(DocumentManager dm,
                              String title, int value,
                              Boolean isReference, Boolean isBestSeller,
                              String publisher, LocalDate dateOfPublishing,
                              int edition, int editionYear) throws Exception {

    }

    public static void delete(DocumentManager dm, int id) {

    }
}
