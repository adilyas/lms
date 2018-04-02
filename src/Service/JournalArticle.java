package Service;

public class JournalArticle extends Document {
    private int journalId;

    public JournalArticle(String title, int journalId) {
        super(title, (int) 2e9, "journal_article");
        this.journalId = journalId;
    }

    public JournalArticle(int id, String title, int journalId) {
        super(id, title, (int) 2e9, "journal_article");
        this.journalId = journalId;
    }

    public int getJournalId() {
        return journalId;
    }

    public void setJournalId(int journalId) {
        this.journalId = journalId;
    }
}
