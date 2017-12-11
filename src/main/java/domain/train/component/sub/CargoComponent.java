package domain.train.component.sub;

import domain.train.component.ComponentDecorator;
import domain.train.component.IComponent;

public class CargoComponent extends ComponentDecorator {

    private final int maxCargo;

    public CargoComponent(IComponent component, int maxCargo) {
        super(component);
        this.maxCargo = maxCargo;
    }

    public int getMaxCargo() {
        return maxCargo;
    }

}
