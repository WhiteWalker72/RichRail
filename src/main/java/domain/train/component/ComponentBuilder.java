package domain.train.component;

import domain.train.component.sub.CargoComponent;
import domain.train.component.sub.LocomotiveComponent;
import domain.train.component.sub.PassagerComponent;


public class ComponentBuilder {

    private IComponent component;

    private final String id;
    private String type;
    private String image;

    private int cargo = 0;
    private int pullingPower = 0;
    private int seats = 0;

    public ComponentBuilder(String id) {
        this.id = id;
        type = "basic";
        image = "basicwagon.png";
    }

    public ComponentBuilder withCargo(int cargo) {
        component = new CargoComponent(component, cargo);
        type = "cargo";
        image = "cargowagon.png";
        this.cargo = cargo;
        return this;
    }

    public ComponentBuilder withPullingPower(int pullingPower) {
        component = new LocomotiveComponent(component, pullingPower);
        type = "locomotive";
        image = "locomotive.png";
        this.pullingPower = pullingPower;
        return this;
    }

    public ComponentBuilder withPassengers(int seats) {
        component = new PassagerComponent(component, seats);
        type = "passenger";
        image = "passengerwagon.png";
        this.seats = seats;
        return this;
    }

    public IComponent build() {
        IComponent component = new BasicComponent(id, type, image);
        if (cargo > 0)
            new CargoComponent(component, cargo);
        if (seats > 0)
            new PassagerComponent(component, seats);
        if (pullingPower > 0)
            new LocomotiveComponent(component,pullingPower);
        return component;
    }

}
