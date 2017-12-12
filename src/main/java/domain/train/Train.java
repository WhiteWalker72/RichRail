package domain.train;

import domain.train.component.IComponent;
import domain.train.component.sub.LocomotiveComponent;
import domain.train.iterator.Iterator;

import java.util.ArrayList;
import java.util.List;

public class Train implements ITrain {

    private final String name;
    private final List<IComponent> components;

    public Train(String name, IComponent firstComponent) {
        this.name = name;
        components = new ArrayList<>();
        components.add(firstComponent);
    }

    public Train(String name, List<IComponent> components) {
        this.name = name;
        this.components = components;
    }

    @Override
    public void addItem(IComponent item) {
        components.add(item);
    }

    @Override
    public void removeItem(IComponent item) {
        components.remove(item);
    }

    @Override
    public IComponent getComponent(String id) {
        for(Iterator<IComponent> iterator = getIterator(); iterator.hasNext();) {
            IComponent component = iterator.getNext();
            if (id.equalsIgnoreCase(component.getId())) {
                return component;
            }
        }
        return null;
    }

    public int getTotalPullingPower() {
        return components.stream()
                .filter(component -> component instanceof LocomotiveComponent)
                .mapToInt(component -> ((LocomotiveComponent) component).getPullingPower()
                ).sum();
    }

    public int getUsedPullingPower() {
        return (int) components.stream()
                .filter(component -> !(component instanceof LocomotiveComponent)).count();
    }

    @Override
    public Iterator<IComponent> getIterator() {
        return new ComponentIterator();
    }

    @Override
    public String getName() {
        return name;
    }

    private class ComponentIterator implements Iterator<IComponent> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < components.size();
        }

        @Override
        public IComponent getNext() {
            return components.get(index++);
        }

    }

}
