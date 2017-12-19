package domain.train;

import domain.train.component.IComponent;
import domain.train.iterator.Iterator;
import domain.train.observer.IObserver;
import domain.train.observer.ISubject;
import model.ComponentDAO;
import model.ComponentDAOMongoImpl;
import utils.Pair;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Has all components that aren't connected to any train
public class ComponentManager implements ISubject {

    private final List<IComponent> components;
    private final ComponentDAO componentDAO;
    private final Set<IObserver> observers = new HashSet<>();
    private final List<String> componentTypes = Arrays.asList("basic", "passenger", "cargo", "locomotive");

    ComponentManager() {
        this.componentDAO = new ComponentDAOMongoImpl();
        components = componentDAO.getAllOtherComponents();
    }

    public Pair<String, IComponent> getComponentPair(String id) {
        for (IComponent component : components)
            if (component.getId().equalsIgnoreCase(id))
                return new Pair<>(null, component);

        for (ITrain train : TrainFacade.getInstance().getTrains()) {
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
        if (getComponentPair(component.getId()) == null) {
            components.add(component);
            return componentDAO.insertComponent(component);
        }
        return false;
    }

    public boolean removeComponent(IComponent component) {
        if (getComponentPair(component.getId()) != null) {
            components.remove(component);
            return componentDAO.removeComponent(component);
        }
        return  false;
    }

    public List getComponentTypes() {
        return componentTypes;
    }

    @Override
    public void register(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unregister(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(observer -> observer.update());
    }

}
