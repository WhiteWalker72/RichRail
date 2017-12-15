package domain.command.subcommands;

import com.sun.org.apache.xpath.internal.SourceTree;
import domain.command.Command;
import domain.train.ITrain;
import domain.train.component.IComponent;
import domain.train.component.sub.PassagerComponent;
import domain.train.iterator.Iterator;
import utils.Pair;

public class GetWagonSeatsCommand extends Command {

    public GetWagonSeatsCommand() {
        super("getnumseats wagon", "getnumseats", "wagon", "name");
    }

    @Override
    public String execute(String[] args) {
        String nameWagon = args[2];
        int numSeatsWagon = 0;

        Pair<String, IComponent> pair = trainFacade.getComponentPair(nameWagon);

        if (pair == null) {
            return couldNotFind("wagon", nameWagon);
        }

        IComponent component = pair.getRightValue();

        if (component instanceof PassagerComponent) {
            numSeatsWagon = ((PassagerComponent) component).getSeats();
        }

        return "number of seats in wagon " + nameWagon + ": " + numSeatsWagon;
    }
}
