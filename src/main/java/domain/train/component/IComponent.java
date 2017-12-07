package domain.train.component;

import utils.Location;

import java.awt.*;

public interface IComponent {

    String getId();

    String getType();

    Location getLocation();

    Image getImage();

}
