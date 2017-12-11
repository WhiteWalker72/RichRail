package domain.train;

import domain.train.component.IComponent;
import model.TrainDAO;
import model.TrainDAOMongoImpl;

import java.util.List;

public class TrainManager {

    private static TrainManager instance;
    private final List<ITrain> trains;
    private final TrainDAO trainDAO;

    private TrainManager() {
        // Using mongo
        trainDAO = new TrainDAOMongoImpl();
        trains = trainDAO.getAllTrains();
    }

    public boolean createTrain(String name, IComponent component) {
        if (getTrain(name) != null)
            return false;
        ITrain train = new Train(name, component);
        trains.add(train);
        return trainDAO.insertTrain(train);
    }

    public boolean updateTrain(ITrain train) {
        return trainDAO.updateTrain(train);
    }

    public boolean deleteTrain(ITrain train) {
        return trainDAO.deleteTrain(train);
    }

    public ITrain getTrain(String trainName) {
        for (ITrain train : trains)
            if (train.getName().equalsIgnoreCase(trainName))
                return train;
        return null;
    }

    public static TrainManager getInstance() {
        if (instance == null)
            instance = new TrainManager();
        return instance;
    }

}
