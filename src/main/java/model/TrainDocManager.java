package model;

import domain.train.component.ComponentBuilder;
import domain.train.component.IComponent;
import domain.train.component.sub.CargoComponent;
import domain.train.component.sub.LocomotiveComponent;
import domain.train.component.sub.PassagerComponent;
import org.bson.Document;
import utils.Location;

import java.util.ArrayList;
import java.util.List;

public class TrainDocManager {

    private final Document doc;
    private List<IComponent> components;

    TrainDocManager(Document doc) {
        this.doc = doc;
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
            for (Document comDoc : (List<Document>) doc.get("components")) {
                ComponentBuilder builder = new ComponentBuilder(
                        comDoc.getString("id")
                        , comDoc.getString("type")
                        , new Location(comDoc.getInteger("loc_x"), comDoc.getInteger("loc_y"))
                        , comDoc.getString("image")
                );
                if (comDoc.containsKey("cargo")) {
                    builder.withCargo(comDoc.getInteger("cargo"));
                }
                if (comDoc.containsKey("seats")) {
                    builder.withPassengers(comDoc.getInteger("seats"));
                }
                if (comDoc.containsKey("power")) {
                    builder.withPullingPower(comDoc.getInteger("power"));
                }
                components.add(builder.build());
            }
        }
        return components;
    }

    void setComponents(List<IComponent> components) {
        List<Document> componentDocs = new ArrayList<>();
        for (IComponent com : components) {
            Document comDoc = new Document("id", com.getId());
            comDoc.put("type", com.getType());
            comDoc.put("loc_x", com.getLocation().getX());
            comDoc.put("loc_y", com.getLocation().getY());
            comDoc.put("image", com.getImage());
            if (com instanceof CargoComponent) {
                comDoc.put("cargo", ((CargoComponent) com).getMaxCargo());
            }
            if (com instanceof PassagerComponent) {
                comDoc.put("seats", ((PassagerComponent) com).getSeats());
            }
            if (com instanceof LocomotiveComponent) {
                comDoc.put("power", ((LocomotiveComponent) com).getPullingPower());
            }
            componentDocs.add(comDoc);
        }
        doc.put("components", componentDocs);
    }

    Document getDoc() {
        return doc;
    }

}
