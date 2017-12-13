package model;

import com.mongodb.client.MongoCollection;
import domain.train.component.IComponent;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ComponentDAOMongoImpl implements ComponentDAO {

    private final MongoCollection<Document> componentCollection;

    public ComponentDAOMongoImpl() {
        this.componentCollection = MlabDB.getInstance().getDatabase().getCollection("components");
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
    public boolean insertComponent(IComponent component) {
        componentCollection.insertOne(new ComponentDocHelper().componentToDoc(component));
        return true;
    }

    @Override
    public boolean removeComponent(IComponent component) {
        return componentCollection.findOneAndDelete(new Document("id", component.getId())) != null;
    }

    private MongoCollection<Document> getComponentCollection() {
        return componentCollection;
    }

}
