package model;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import domain.train.ITrain;
import domain.train.Train;
import domain.train.component.IComponent;
import domain.train.iterator.Iterator;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainDAOMongoImpl implements TrainDAO {

    private final MongoCollection<Document> trainCollection;
    private final MongoCollection<Document> componentCollection;
    private final Map<String, TrainDocManager> trainMap = new HashMap<>();

    public TrainDAOMongoImpl() {
        this.trainCollection = MlabDB.getInstance().getDatabase().getCollection("trains");
        this.componentCollection = MlabDB.getInstance().getDatabase().getCollection("components");
    }

    @Override
    public List<ITrain> getAllTrains() {
        List<ITrain> trains = new ArrayList<>();
        for (Document trainDoc : getTrainCollection().find()) {
            TrainDocManager manager = new TrainDocManager(trainDoc);
            trains.add(new Train(manager.getName(), manager.getComponents()));
        }
        return trains;
    }

    @Override
    public List<IComponent> getAllOtherComponents() {
        ComponentDocHelper helper = new ComponentDocHelper();
        List<IComponent> allComponents = new ArrayList<>();

        for (Document comDoc : getComponentCollection().find()) {
            allComponents.add(helper.docToComponent(comDoc));
        }
        return allComponents;
    }

    @Override
    public boolean insertTrain(ITrain train) {
        TrainDocManager existingManager = getTrainDocManager(train.getName());
        if (existingManager != null)
            return false;

        TrainDocManager manager = new TrainDocManager(new Document("name", train.getName()));
        manager.setComponents(getTrainComponents(train));
        getTrainCollection().insertOne(manager.getDoc());
        return true;
    }

    @Override
    public boolean updateTrain(ITrain train) {
        TrainDocManager manager = getTrainDocManager(train.getName());
        if (manager == null)
            return false;
        manager.setComponents(getTrainComponents(train));
        Document doc = manager.getDoc();
        getTrainCollection().updateOne(new Document("_id", doc.get("_id")), new Document("$set", doc));
        return true;
    }

    @Override
    public boolean deleteTrain(ITrain train) {
        TrainDocManager manager = getTrainDocManager(train.getName());
        if (manager == null)
            return false;
        getTrainCollection().findOneAndDelete(new Document("_id", manager.getDoc().get("_id")));
        return true;
    }

    @Override
    public boolean insertComponent(IComponent component) {
        componentCollection.insertOne(new ComponentDocHelper().componentToDoc(component));
        return true;
    }

    @Override
    public boolean removeComponent(IComponent component) {
        return componentCollection.findOneAndDelete(new Document("id", component.getId())) != null;
    }

    private TrainDocManager getTrainDocManager(String name) {
        TrainDocManager trainDocManager = trainMap.get(name);
        if (trainDocManager == null) {
            FindIterable<Document> findIterable = getTrainCollection().find(new Document("name", name));
            Document result = findIterable.first();
            if (result == null) {
                return null;
            }
            trainDocManager = new TrainDocManager(result);
            trainMap.put(name, trainDocManager);
        }
        return trainDocManager;
    }

    private List<IComponent> getTrainComponents(ITrain train) {
        List<IComponent> components = new ArrayList<>();
        for(Iterator<IComponent> iterator = train.getIterator(); iterator.hasNext();) {
            components.add(iterator.getNext());
        }
        return components;
    }

    private MongoCollection<Document> getTrainCollection() {
        return trainCollection;
    }

    private MongoCollection<Document> getComponentCollection() {
        return componentCollection;
    }

}
