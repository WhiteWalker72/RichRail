package domain.train.component;

public class BasicComponent implements IComponent {

    private final String id;
    private final String type;
    private final String image;

    public BasicComponent(String id, String type, String image) {
        this.id = id;
        this.type = type;
        this.image = image;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getImage() {
        return image;
    }

}
