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

        Pair<String, IComponent> pair = trainFacade.getComponentPair(wagonName);

        if (pair == null) {
            return couldNotFind("wagon", wagonName);
        }

        if (pair.getLeftValue() != null) {
            String trainName = pair.getLeftValue();
            ITrain train = trainFacade.getTrain(trainName);

            IComponent component = pair.getRightValue();
            train.removeItem(component);
            trainFacade.updateTrain(train);

            return "wagon " + wagonName + " from train " + trainName + " deleted";
        }

        IComponent component = pair.getRightValue();
        trainFacade.removeComponent(component);

        return "wagon " + wagonName + " deleted";
    }
}

