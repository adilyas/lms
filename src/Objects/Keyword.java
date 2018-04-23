package Objects;

public class Keyword {
    private String word;
    private int id;

    public Keyword(String word) {
        this.word = word;
    }

    public Keyword(int id, String word) {
        this.id = id;
        this.word = word;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }
}
