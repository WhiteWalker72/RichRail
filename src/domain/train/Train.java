package domain.train;

import domain.train.component.IComponent;
import domain.train.iterator.Container;
import domain.train.iterator.Iterator;

import java.util.ArrayList;
import java.util.List;

public class Train implements Container<IComponent> {

    private final String name;
    private final List<IComponent> components = new ArrayList<>();

    public Train(String name, IComponent firstComponent) {
        this.name = name;
        components.add(firstComponent);
    }

    @Override
    public void addItem(IComponent item) {
        components.add(item);
    }

    @Override
    public void removeItem(IComponent item) {
        components.remove(item);
    }

    public IComponent getComponent(String id) {
        for(Iterator<IComponent> iterator = getIterator(); iterator.hasNext();) {
            IComponent component = iterator.getNext();
            if (id.equalsIgnoreCase(component.getId())) {
                return component;
            }
        }
        return null;
    }

    @Override
    public Iterator<IComponent> getIterator() {
        return new ComponentIterator();
    }

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
