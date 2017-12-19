package domain.train;

import domain.train.component.IComponent;
import domain.train.observer.IObserver;
import utils.Pair;

import java.util.List;

public class TrainFacade {

    private static TrainFacade instance;
    private TrainManager trainManager;
    private ComponentManager componentManager;

    private TrainFacade() {
        this.trainManager = new TrainManager();
        this.componentManager = new ComponentManager();
    }

    public boolean validTrainName(String name) {
        return trainManager.validTrainName(name);
    }

    public boolean createTrain(String name, IComponent component) {
        return trainManager.createTrain(name, component);
    }

    public boolean updateTrain(ITrain train) {
        return trainManager.updateTrain(train);
    }

    public boolean deleteTrain(ITrain train) {
        return trainManager.deleteTrain(train);
    }

    public ITrain getTrain(String trainName) {
        return trainManager.getTrain(trainName);
    }

    public List<ITrain> getTrains() {
        return trainManager.getTrains();
    }

    public Pair<String, IComponent> getComponentPair(String id) {
        return componentManager.getComponentPair(id);
    }

    public boolean validComponentId(String comId) {
        return componentManager.validComponentId(comId);
    }

    public boolean insertComponent(IComponent component) {
        return componentManager.insertComponent(component);
    }

    public boolean removeComponent(IComponent component) {
        return componentManager.removeComponent(component);
    }

    public List<String> getComponentTypes() {
        return componentManager.getComponentTypes();
    }

    public void registerTrainObserver(IObserver observer) {
        trainManager.register(observer);
    }

    public void unregisterTrainObserver(IObserver observer) {
        trainManager.unregister(observer);
    }

    public void registerComponentObserver(IObserver observer) {
        componentManager.register(observer);
    }

    public void unregisterComponentObserver(IObserver observer) {
        componentManager.unregister(observer);
    }

    public static TrainFacade getInstance() {
        if (instance == null)
            instance = new TrainFacade();
        return instance;
    }

}
