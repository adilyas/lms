package Objects;

public class AVMaterial extends Document {
    public AVMaterial(String title, int value, String type) {
        super(title, value, "av_material");
    }

    public AVMaterial(int id, String title, int value, String type) {
        super(id, title, value, "av_material");
    }
}