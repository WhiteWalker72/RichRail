package domain.train;

import domain.train.iterator.Container;
import domain.train.iterator.Iterator;

import java.util.ArrayList;
import java.util.List;

public class TrainManager implements Container<Train> {

    private static TrainManager instance;
    private final List<Train> trains = new ArrayList<>();

    private TrainManager() {

    }

    @Override
    public void addItem(Train item) {
        trains.add(item);
    }

    @Override
    public void removeItem(Train item) {
        trains.remove(item);
    }

    public Train getTrain(String trainName) {
        for(Iterator<Train> iterator = getIterator(); iterator.hasNext();) {
            Train train = iterator.getNext();
            if (trainName.equalsIgnoreCase(train.getName())) {
                return train;
            }
        }
        return null;
    }

    @Override
    public Iterator getIterator() {
        return new TrainIterator();
    }

    private class TrainIterator implements Iterator<Train> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < trains.size();
        }

        @Override
        public Train getNext() {
            return trains.get(index++);
        }

    }

    public static TrainManager getInstance() {
        if (instance == null)
            instance = new TrainManager();
        return instance;
    }

}
