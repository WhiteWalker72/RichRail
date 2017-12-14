package domain.command.subcommands;

import domain.command.Command;
import domain.train.component.ComponentBuilder;
import utils.MathUtils;
import utils.option.IOption;

public class NewWagonCommand extends Command {
    public NewWagonCommand() {
        super("new wagon", 3, "new", "wagon", "name", "type", "amount");
    }

    @Override
    public String execute(String[] args) {
        String wagonName = args[2].toLowerCase();
        String typeWagon;

        try {
            typeWagon = args[3];
        } catch (Exception e) {
            typeWagon = "BasicComponent";
        }

        int amount = 0;

        if (args.length > 3) {
            try {
                amount = Integer.parseInt(args[4]);
            } catch (Exception e) {
                amount = 40;
            }
        }

        if (!trainManager.getComponentManager().validComponentId(wagonName)) {
            return "Invalid name for " + wagonName + ".";
        }

        if (trainManager.getComponentManager().getComponentPair(wagonName) != null) {
            return "component " + wagonName + " already exists.";
        }

        if (typeWagon.equals("BasicComponent")) {
            // Maak een basic wagon
            return trainManager.getComponentManager().insertComponent(new ComponentBuilder(wagonName).build()) ?
                    "wagon " + wagonName + " created" : "wagon " + wagonName + " already exists.";
        }

        if (typeWagon.equals("PassagerComponent")) {

            // Maak een passagierswagon
            return trainManager.getComponentManager().insertComponent(new ComponentBuilder(wagonName).withPassengers(amount).build()) ?
                    "wagon " + wagonName + " with " + amount + " passagers created" : "wagon " + wagonName + " already exists.";

        }

        if (typeWagon.equals("CargoComponent")) {

            // Maak een cargowagon
            return trainManager.getComponentManager().insertComponent(new ComponentBuilder(wagonName).withCargo(amount).build()) ?
                    "wagon " + wagonName + " with " + amount + " hoeveelheid cargo created" : "wagon " + wagonName + " already exists.";
        }

        return "Het is niet gelukt om een wagon aan te maken";
    }
}