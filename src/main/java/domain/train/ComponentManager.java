package domain.train;

import domain.train.component.IComponent;
import domain.train.iterator.Iterator;
import model.TrainDAO;
import utils.Pair;

import java.util.List;

// Has all components that aren't connected to any train
public class ComponentManager {

    private final List<IComponent> components;
    private final TrainDAO trainDAO;

    ComponentManager(TrainDAO trainDAO) {
        this.trainDAO = trainDAO;
        components = trainDAO.getAllOtherComponents();
    }

    public Pair<String, IComponent> getComponentPair(String id) {
        for (IComponent component : components)
            if (component.getId().equalsIgnoreCase(id))
                return new Pair<>(null, component);

        for (ITrain train : TrainManager.getInstance().getTrains()) {
            for(Iterator<IComponent> iterator = train.getIterator(); iterator.hasNext();) {
                IComponent component = iterator.getNext();
                if (component.getId().equalsIgnoreCase(id)) {
                    return new Pair<>(train.getName(), component);
                }
            }
        }
        return null;
    }

    public boolean validComponentId(String comId) {
        if (comId.matches("[a-z][a-z|0-9]*"))
            return comId.length() <= 10;
        return false;
    }

    public boolean insertComponent(IComponent component) {
        return getComponentPair(component.getId()) != null ? false: trainDAO.insertComponent(component);
    }
    public boolean removeComponent(IComponent component) {
        return getComponentPair(component.getId()) == null ? false : trainDAO.removeComponent(component);
    }
}
