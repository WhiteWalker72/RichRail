package domain.command.subcommands;

import domain.command.Command;
import domain.train.ITrain;

public class DeleteTrainCommand extends Command {

    public DeleteTrainCommand() {
        super("delete train", "delete", "train", "name");
    }

    @Override
    public String execute(String[] args) {
        String trainName = args[2];
        ITrain train = trainFacade.getTrain(trainName);

        if (train == null) {
            return couldNotFind("train", trainName);
        }

        if (trainFacade.deleteTrain(train)) {
            return "train " + trainName + " deleted";
        }
        return "couldn't delete train " + trainName;
    }

}
