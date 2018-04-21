package Objects;

import java.util.Collection;

public class AVMaterial extends Document {
    public AVMaterial(String title, int value, Collection<Author> authors, Collection<Keyword> keywords) {
        super("av_material", title, value, authors, keywords);
    }

    public AVMaterial(int id, String title, int value, Collection<Author> authors, Collection<Keyword> keywords) {
        super(id, "av_material", title, value, authors, keywords);
    }
}