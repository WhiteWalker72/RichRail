package domain.command.subcommands;

import domain.command.Command;
import domain.train.ITrain;
import domain.train.component.IComponent;
import utils.Pair;

public class AddToCommand extends Command {

    public AddToCommand() {
        super("add", "add", "wagon", "to", "train");
    }

    @Override
    public String execute(String[] args) {
        String trainName = args[3];
        ITrain targetTrain = trainManager.getTrain(trainName);
        if (targetTrain == null) {
            return couldNotFind("train", trainName);
        }

        if (targetTrain.getTotalPullingPower() >= targetTrain.getUsedPullingPower()) {
            return targetTrain.getName() + " reached it's max pulling power";
        }
        String wagonId = args[1];

        Pair<String, IComponent> componentPair = trainManager.getComponentManager().getComponentPair(wagonId);
        if (componentPair == null) {
            return "Wagon '" + wagonId + "' does not exist";
        }
        IComponent component = componentPair.getRightValue();

        // Remove from the train that is using the component
        if (componentPair.getLeftValue() != null) {
            ITrain train = trainManager.getTrain(componentPair.getLeftValue());
            if (train != null) {
                train.removeItem(component);
                addToTargetTrain(targetTrain, component);
                return "wagon " + component.getId() + " removed from train " + train.getName() + " and added to " + targetTrain.getName();
            }
            return "train wasn't found..";
        } else {
            trainManager.getComponentManager().removeComponent(component);
        }
        addToTargetTrain(targetTrain, component);

        return "wagon " + component.getId() + " added to " + targetTrain.getName();
    }

    private void addToTargetTrain(ITrain targetTrain, IComponent component) {
        targetTrain.addItem(component);
        trainManager.updateTrain(targetTrain);
    }

}
