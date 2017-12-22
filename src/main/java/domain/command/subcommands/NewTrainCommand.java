package domain.command.subcommands;

import domain.command.Command;
import domain.train.component.ComponentBuilder;
import domain.train.component.IComponent;
import utils.MathUtils;
import utils.option.IOption;

public class NewTrainCommand extends Command {

    public NewTrainCommand() {
        super("new train", 3, "new", "train", "name", "power");
    }

    @Override
    public String execute(String[] args) {
        String trainName = args[2].toLowerCase();
        if (!trainFacade.validTrainName(trainName)) {
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

        if (trainFacade.getComponentPair(trainName) != null) {
            return "component " + trainName + " already exists.";
        }

        ComponentBuilder locoBuilder = new ComponentBuilder(trainName).withPullingPower(power);
        if (trainName.contains("thomas")) {
            locoBuilder.withCustomImage("thomas.png");
        }

        return trainFacade.createTrain(trainName, locoBuilder.build()) ?
                "train " + trainName + " created" : "train " + trainName + " already exists.";
    }

}