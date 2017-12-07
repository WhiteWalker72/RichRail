package domain.train.component.sub;

import domain.train.component.ComponentDecorator;
import domain.train.component.IComponent;

public class LocomotiveComponent extends ComponentDecorator {

    private final int pullingPower;

    public LocomotiveComponent(IComponent component, int pullingPower) {
        super(component);
        this.pullingPower = pullingPower;
    }

    public int getPullingPower() {
        return pullingPower;
    }

}
