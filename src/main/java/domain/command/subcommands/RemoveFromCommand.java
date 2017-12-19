package domain.command.subcommands;

import domain.command.Command;
import domain.train.ITrain;
import domain.train.component.IComponent;

public class RemoveFromCommand extends Command {

    public RemoveFromCommand() {
        super("remove", "remove", "wagon", "from", "train");
    }

    @Override
    public String execute(String[] args) {
        String wagonId = args[1];
        String trainName = args[3];

        ITrain train = trainFacade.getTrain(trainName);
        if (train == null) {
            return couldNotFind("train", trainName);
        }

        IComponent component = train.getComponent(wagonId);
        if (component == null) {
            return couldNotFind("wagon", wagonId);
        }

        train.removeItem(component);
        trainFacade.updateTrain(train);
        trainFacade.insertComponent(component);
        return "wagon " + wagonId + " removed from train " + trainName;
    }

}
