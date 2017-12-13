package domain.command.subcommands;

import domain.command.Command;
import domain.train.ComponentManager;
import domain.train.ITrain;
import domain.train.TrainManager;
import domain.train.component.IComponent;
import utils.Pair;

public class DeleteWagonCommand extends Command {

    public DeleteWagonCommand() {
        super("delete wagon", "delete", "wagon", "name");
    }

    @Override
    public String execute(String[] args) {
        String wagonName = args[2];

        TrainManager trainManager = TrainManager.getInstance();
        Pair<String, IComponent> pair = trainManager.getComponentManager().getComponentPair(wagonName);

        if (pair == null) {
            return couldNotFind("wagon", wagonName);
        } else if (pair.getLeftValue() != null) {
            String trainName = pair.getLeftValue();
            ITrain train = trainManager.getTrain(trainName);

            IComponent component = pair.getRightValue();

            train.removeItem(component);

            trainManager.updateTrain(train);

            //TODO:
            return "wagon " + wagonName + " from train " + trainName + " deleted";
        } else {

            IComponent component = pair.getRightValue();

            trainManager.getComponentManager().removeComponent(component);

            //TODO:
            return "wagon " + wagonName + " deleted";
        }
    }
}
