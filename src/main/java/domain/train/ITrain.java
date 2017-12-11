package domain.train;

import domain.train.component.IComponent;
import domain.train.iterator.Container;

public interface ITrain extends Container<IComponent> {

    String getName();
    IComponent getComponent(String id);

}
