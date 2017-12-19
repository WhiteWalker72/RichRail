package domain.command.subcommands;

import domain.command.Command;
import domain.train.ITrain;
import domain.train.component.IComponent;
import domain.train.component.sub.LocomotiveComponent;
import utils.Pair;

public class AddToCommand extends Command {

    public AddToCommand() {
        super("add", "add", "wagon", "to", "train");
    }

    @Override
    public String execute(String[] args) {
        String trainName = args[3];
        ITrain targetTrain = trainFacade.getTrain(trainName);
        if (targetTrain == null) {
            return couldNotFind("train", trainName);
        }

        String wagonId = args[1];
        Pair<String, IComponent> componentPair = trainFacade.getComponentPair(wagonId);
        if (componentPair == null) {
            return "wagon '" + wagonId + "' does not exist";
        }
        IComponent component = componentPair.getRightValue();

        int wagonPower = component instanceof LocomotiveComponent ? ((LocomotiveComponent) component).getPullingPower() : 0;
        if (targetTrain.getUsedPullingPower() >= (targetTrain.getTotalPullingPower() + wagonPower)) {
            return targetTrain.getName() + " reached it's max pulling power";
        }

        // Remove from the train that is using the component
        if (componentPair.getLeftValue() != null) {
            ITrain train = trainFacade.getTrain(componentPair.getLeftValue());
            if (train != null) {
                if (train.equals(targetTrain)) {
                    return "wagon " + component.getId() + " is already attached to train " + targetTrain.getName();
                }
                train.removeItem(component);
                trainFacade.updateTrain(train);
                addToTargetTrain(targetTrain, component);
                return "wagon " + component.getId() + " removed from train " + train.getName() + " and added to " + targetTrain.getName();
            }
            return "train wasn't found..";
        } else {
            trainFacade.removeComponent(component);
        }
        addToTargetTrain(targetTrain, component);

        return "wagon " + component.getId() + " added to " + targetTrain.getName();
    }

    private void addToTargetTrain(ITrain targetTrain, IComponent component) {
        targetTrain.addItem(component);
        trainFacade.updateTrain(targetTrain);
    }

}
