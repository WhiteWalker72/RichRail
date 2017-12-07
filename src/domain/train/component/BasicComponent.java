package domain.train.component;

import utils.Location;

import java.awt.*;

public class BasicComponent implements IComponent {

    private final String id;
    private final String type;
    private final Location location;
    private final Image image;

    public BasicComponent(String id, String type, Location location, Image image) {
        this.id = id;
        this.type = type;
        this.location = location;
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
    public Location getLocation() {
        return location;
    }

    @Override
    public Image getImage() {
        return image;
    }

}
