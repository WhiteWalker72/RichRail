package domain.command.subcommands;

import domain.command.Command;
import domain.train.ITrain;
import domain.train.component.IComponent;
import domain.train.component.sub.PassagerComponent;
import domain.train.iterator.Iterator;

public class GetWagonSeatsCommand extends Command {

    public GetWagonSeatsCommand() {
        super("getnumseats wagon", "getnumseats", "wagon", "name");
    }

    @Override
    public String execute(String[] args) {
        String naamWagon = args[2];
        int numSeatsWagon = 0;

        ITrain train = trainManager.getTrain(naamBijbehorendeTrein);

        for (Iterator<IComponent> iterator = train.getIterator(); iterator.hasNext(); ) {
            IComponent component = iterator.getNext();
            if (component instanceof PassagerComponent) {
                numSeatsWagon =+ ((PassagerComponent) component).getSeats();
            }
        }

        return "number of seats in wagon test: " + numSeatsWagon;
    }

}
