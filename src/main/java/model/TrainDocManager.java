package model;

import domain.train.component.IComponent;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

class TrainDocManager {

    private final Document doc;
    private List<IComponent> components;
    private final ComponentDocHelper docHelper;

    TrainDocManager(Document doc) {
        this.doc = doc;
        this.docHelper = new ComponentDocHelper();
    }

    String getName() {
        return doc.getString("name");
    }

    List<IComponent> getComponents() {
        if (components == null) {
            components = new ArrayList<>();
            if (!doc.containsKey("components")) {
                return components;
            }
            ((List<Document>) doc.get("components")).forEach(comDoc -> components.add(docHelper.docToComponent(comDoc)));
        }
        return components;
    }

    void setComponents(List<IComponent> components) {
        List<Document> componentDocs = new ArrayList<>();
        for (IComponent com : components) {
            componentDocs.add(docHelper.componentToDoc(com));
        }
        doc.put("components", componentDocs);
    }

    Document getDoc() {
        return doc;
    }

}
