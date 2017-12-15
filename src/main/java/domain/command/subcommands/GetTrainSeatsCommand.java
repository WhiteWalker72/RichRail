package domain.command.subcommands;

import domain.command.Command;
import domain.train.ITrain;
import domain.train.component.IComponent;
import domain.train.component.sub.PassagerComponent;
import domain.train.iterator.Iterator;

public class GetTrainSeatsCommand extends Command {

    public GetTrainSeatsCommand() {
        super("getnumseats train", "getnumseats", "train", "name");
    }

    @Override
    public String execute(String[] args) {
        int totalNumberOfSeats = 0;
        String trainName = args[2];

        ITrain train = trainFacade.getTrain(trainName);

        if (train == null) {
            return couldNotFind("train", trainName);
        }

        for (Iterator<IComponent> iterator = train.getIterator(); iterator.hasNext(); ) {
            IComponent component = iterator.getNext();
            if (component instanceof PassagerComponent) {
                totalNumberOfSeats += ((PassagerComponent) component).getSeats();
            }
        }
        return "number of seats in train " + trainName + ": " + totalNumberOfSeats;
    }
}


