package model;

import domain.train.component.ComponentBuilder;
import domain.train.component.IComponent;
import domain.train.component.sub.CargoComponent;
import domain.train.component.sub.LocomotiveComponent;
import domain.train.component.sub.PassagerComponent;
import org.bson.Document;

class ComponentDocHelper {

    ComponentDocHelper() {

    }

    Document componentToDoc(IComponent com) {
        Document comDoc = new org.bson.Document("id", com.getId());
        comDoc.put("type", com.getType());
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
        return comDoc;
    }

    IComponent docToComponent(Document comDoc) {
        ComponentBuilder builder = new ComponentBuilder(
                comDoc.getString("id")
                , comDoc.getString("type")
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
        return builder.build();
    }

}
