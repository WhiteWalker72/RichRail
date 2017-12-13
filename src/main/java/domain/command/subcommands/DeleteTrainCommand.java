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
        ITrain train = trainManager.getTrain(trainName);

        if (train == null) {
            return couldNotFind("train", trainName);
        }

        trainManager.deleteTrain(train);

        //TODO:
        return "train " + trainName + " deleted";
    }

}
