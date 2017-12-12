package domain.train.component;

public class ComponentDecorator implements IComponent {

    private final IComponent component;

    public ComponentDecorator(IComponent component) {
        this.component = component;
    }

    @Override
    public String getId() {
        return component.getId();
    }

    @Override
    public String getType() {
        return component.getType();
    }

    @Override
    public String getImage() {
        return component.getImage();
    }

}
