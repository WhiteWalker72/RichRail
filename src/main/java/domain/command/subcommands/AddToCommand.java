package domain.command.subcommands;

import domain.command.Command;
import domain.train.ITrain;
import domain.train.component.IComponent;

public class AddToCommand extends Command {

    public AddToCommand() {
        super("add", "add", "wagon", "to", "train");
    }

    @Override
    public String execute(String[] args) {
        String trainName = args[3];
        ITrain train = trainManager.getTrain(trainName);
        if (train == null) {
            return couldNotFind("train", trainName);
        }

        String wagonId = args[1];
        IComponent component = train.getComponent(wagonId);
        if (component == null) {
            return couldNotFind("wagon", wagonId);
        }

        if (train.getTotalPullingPower() >= train.getUsedPullingPower()) {
            return train.getName() + " reached it's max pulling power";
        }

        //TODO:
        return "wagon testwagon added to testtrain";
    }

}
