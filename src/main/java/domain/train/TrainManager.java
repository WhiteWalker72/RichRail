package domain.train;

import domain.train.component.IComponent;
import domain.train.observer.IObserver;
import domain.train.observer.ISubject;
import model.TrainDAO;
import model.TrainDAOMongoImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TrainManager implements ISubject {

    private final List<ITrain> trains;
    private final Set<IObserver> observers = new HashSet<>();
    private final TrainDAO trainDAO;

    TrainManager() {
        // Using mongo
        trainDAO = new TrainDAOMongoImpl();
        trains = trainDAO.getAllTrains();
    }

    public boolean validTrainName(String name) {
        if (name.matches("[a-z][a-z|0-9]*"))
            return name.length() <= 10;
        return false;
    }

    public boolean createTrain(String name, IComponent component) {
        if (getTrain(name) != null)
            return false;
        ITrain train = new Train(name, component);
        boolean inserted = trainDAO.insertTrain(train);

        if (inserted) {
            trains.add(train);
            notifyObservers();
        }
        return inserted;
    }

    public boolean updateTrain(ITrain train) {
        boolean updated = trainDAO.updateTrain(train);
        if (updated) {
            notifyObservers();
        }
        return updated;
    }

    public boolean deleteTrain(ITrain train) {
        boolean deleted = trainDAO.deleteTrain(train);
        if (deleted) {
            trains.remove(train);
            notifyObservers();
        }
        return deleted;
    }

    public ITrain getTrain(String trainName) {
        for (ITrain train : trains)
            if (train.getName().equalsIgnoreCase(trainName))
                return train;
        return null;
    }

    public List<ITrain> getTrains() {
        return trains;
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
