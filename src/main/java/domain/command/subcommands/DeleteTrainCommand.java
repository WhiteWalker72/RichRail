package domain.command.subcommands;

import domain.command.Command;
import domain.train.ITrain;

public class DeleteTrainCommand extends Command {

    public DeleteTrainCommand() {
        super("delete train", "delete", "train", "type");
    }

    @Override
    public String execute(String[] args) {
        String trainName = args[1];
        ITrain train = trainManager.getTrain(trainName);

        if (train == null) {
            return couldNotFind("train", trainName);
        }

        if (trainManager.deleteTrain(train)) {
            return "train " + trainName + " deleted";
        }
        return "couldn't delete train " + trainName;
    }

}
