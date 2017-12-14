package model;

import domain.train.component.IComponent;

import java.util.List;

public interface ComponentDAO {

    List<IComponent> getAllOtherComponents();
    boolean insertComponent(IComponent component);
    boolean removeComponent(IComponent component);

}
