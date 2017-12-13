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

    // Pair has has the train name as left value
    public Pair<String, IComponent> getTrainComponent(String id) {
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

    public IComponent getOtherComponent(String id) {
        for (IComponent component : components)
            if (component.getId().equalsIgnoreCase(id))
                return component;
        return null;
    }

    public boolean componentExists(String id) {
        return getOtherComponent(id) != null || getTrainComponent(id) != null;
    }

    public boolean validComponentId(String comId) {
        //TODO: regex
        return comId.length() <= 10;
    }

    public boolean insertComponent(IComponent component) {
        return componentExists(component.getId()) ? true : trainDAO.insertComponent(component);
    }

    public boolean removeComponent(IComponent component) {
        return componentExists(component.getId()) ? trainDAO.removeComponent(component) : false;
    }

}
