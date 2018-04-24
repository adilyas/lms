package Objects;

import java.awt.event.KeyEvent;
import java.util.Collection;

public class AVMaterial extends Document {
    public AVMaterial(String title, int value, boolean outstandingRequest, Collection<Author> authors,
                      Collection<Keyword> keywords, Collection<Patron> bookedBy, Collection<Copy> copies) {
        super("av_material", title, value, outstandingRequest, authors, keywords, bookedBy, copies);
    }

    public AVMaterial(int id, String title, int value, boolean outstandingRequest, Collection<Author> authors,
                      Collection<Keyword> keywords, Collection<Patron> bookedBy, Collection<Copy> copies) {
        super(id, "av_material", title, value, outstandingRequest, authors, keywords, bookedBy, copies);
    }
}