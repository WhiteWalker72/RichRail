package model;

import domain.train.ITrain;
import domain.train.component.IComponent;

import java.util.List;

public interface TrainDAO {

    List<ITrain> getAllTrains();
    boolean insertTrain(ITrain train);
    boolean updateTrain(ITrain train);
    boolean deleteTrain(ITrain train);

}
