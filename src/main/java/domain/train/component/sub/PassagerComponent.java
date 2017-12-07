package domain.train.component.sub;

import domain.train.component.ComponentDecorator;
import domain.train.component.IComponent;

public class PassagerComponent extends ComponentDecorator {

    private final int seats;

    public PassagerComponent(IComponent component, int seats) {
        super(component);
        this.seats = seats;
    }

    public int getSeats() {
        return seats;
    }

}
