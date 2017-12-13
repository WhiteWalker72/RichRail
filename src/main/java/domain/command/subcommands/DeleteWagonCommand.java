package domain.command.subcommands;

import domain.command.Command;
import domain.train.ITrain;
import domain.train.TrainManager;

public class DeleteWagonCommand extends Command {

    public DeleteWagonCommand() {
        super("delete wagon",  "delete", "wagon", "type");
    }

    @Override
    public String execute(String[] args) {
        String trainName = args[];
        String wagonName = args[1];

        TrainManager trainManager = TrainManager.getInstance();
        ITrain train = trainManager.getTrain(trainName: trainName);

        if (train == null) {
            return couldNotFind("train", trainName);
        }

        train.removeItem(train.getComponent(id: wagonName));

        Wagon wagon = trainManager.getTrain().getComponent();

        if (wagon == null) {
            return couldNotFind("wagon", wagonName);
        }

        trainManager.updateTrain(train);


        //TODO:
        return "wagon test deleted";
    }

}
