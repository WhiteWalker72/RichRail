package domain.command.subcommands;

import domain.command.Command;
import domain.train.component.ComponentBuilder;
import utils.MathUtils;
import utils.option.IOption;

public class NewTrainCommand extends Command {

    public NewTrainCommand() {
        super("new train", 3, "new", "train", "name", "power");
    }

    @Override
    public String execute(String[] args) {
        String trainName = args[2].toLowerCase();
        if (!trainManager.validTrainName(trainName)) {
            return "Invalid name for " + trainName + ".";
        }

        int power = 10;
        if (args.length > 3) {
            IOption<Integer> powerOption = MathUtils.parseInt(args[3]);
            if (powerOption.isNone()) {
                return couldNotFind("power", args[3]);
            }
            power = powerOption.visit(() -> 10, x -> x);
        }

        if (trainManager.getComponentManager().componentExists(trainName)) {
            return "component " + trainName + " already exists.";
        }
        return trainManager.createTrain(trainName, new ComponentBuilder(trainName).withPullingPower(power).build()) ?
                "train " + trainName + " created" : "train " + trainName + " already exists.";
    }

}