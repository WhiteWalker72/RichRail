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
        String naamWagon = args[2];
        int numSeatsWagon = 0;

        Pair<String, IComponent> pair = trainManager.getComponentManager().getComponentPair(naamWagon);

        if (pair == null) {
            return couldNotFind("wagon", naamWagon);
        }

        IComponent component = pair.getRightValue();
        System.out.println(component.getId());

        if (component instanceof PassagerComponent) {
            numSeatsWagon = ((PassagerComponent) component).getSeats();
            System.out.println("numSeatsWagon = " + numSeatsWagon);
        }

        return "number of seats in wagon " + naamWagon + ": " + numSeatsWagon;
    }

}
