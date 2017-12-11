package domain.train.component;

import domain.train.component.sub.CargoComponent;
import domain.train.component.sub.LocomotiveComponent;
import domain.train.component.sub.PassagerComponent;
import utils.Location;


public class ComponentBuilder {

    private IComponent component;

    public ComponentBuilder(String id, String type, Location location, String image) {
        this(new BasicComponent(id, type, location, image));
    }

    public ComponentBuilder(IComponent component) {
        this.component = component;
    }

    public ComponentBuilder withCargo(int cargo) {
        component = new CargoComponent(component, cargo);
        return this;
    }

    public ComponentBuilder withPullingPower(int pullingPower) {
        component = new LocomotiveComponent(component, pullingPower);
        return this;
    }

    public ComponentBuilder withPassengers(int seats) {
        component = new PassagerComponent(component, seats);
        return this;
    }

    public IComponent build() {
        return component;
    }

}
