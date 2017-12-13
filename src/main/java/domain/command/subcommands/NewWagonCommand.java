package domain.command.subcommands;

import domain.command.Command;
import domain.train.component.ComponentBuilder;
import utils.MathUtils;
import utils.option.IOption;

public class NewWagonCommand extends Command {

    public NewWagonCommand() {

        super("new wagon", "new", "wagon", "name");
    }

    @Override
    public String execute(String[] args) {
        String wagonName = args[2].toLowerCase();
        if (!trainManager.validName(wagonName)) {
            return "Invalid name for " + wagonName + ".";
        }



        if (trainManager.getComponentManager().getComponentPair(wagonName) != null) {
            return "component " + wagonName + " already exists.";
        }
        return trainManager.createTrain(wagonName, new ComponentBuilder(wagonName).build()) ?
                "Wagon " + wagonName + " created" : "train " + wagonName + " already exists.";
    }

}
