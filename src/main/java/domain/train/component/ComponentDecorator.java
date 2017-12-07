package domain.train.component;

import utils.Location;

import java.awt.*;

public class ComponentDecorator implements IComponent {

    private final IComponent component;

    public ComponentDecorator(IComponent component) {
        this.component = component;
    }

    @Override
    public String getId() {
        return component.getId();
    }

    @Override
    public String getType() {
        return component.getType();
    }

    @Override
    public Location getLocation() {
        return component.getLocation();
    }

    @Override
    public Image getImage() {
        return component.getImage();
    }

}
