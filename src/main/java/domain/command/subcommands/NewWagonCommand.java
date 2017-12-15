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
        String wagonType;

        try {
            wagonType = args[3].toLowerCase();
        } catch (Exception e) {
            wagonType = "basic";
        }
        if (!componentExists(wagonType)) {
            return couldNotFind("wagontype", wagonType);
        }

        int amount = 0;

        if (args.length > 3) {
            try {
                amount = Integer.parseInt(args[4]);
            } catch (Exception e) {
                amount = 40;
            }
        }

        if (!trainFacade.validComponentId(wagonName)) {
            return "Invalid name for " + wagonName + ".";
        }

        if (trainFacade.getComponentPair(wagonName) != null) {
            return "component " + wagonName + " already exists.";
        }

        if (wagonType.equals("basic")) {
            // Maak een basic wagon
            return trainFacade.insertComponent(new ComponentBuilder(wagonName).build()) ?
                    "basic wagon " + wagonName + " created" : "wagon " + wagonName + " already exists.";
        }

        if (wagonType.equals("passenger")) {
            // Maak een passagierswagon
            return trainFacade.insertComponent(new ComponentBuilder(wagonName).withPassengers(amount).build()) ?
                    "wagon " + wagonName + " with " + amount + " passengers created" : "wagon " + wagonName + " already exists.";

        }

        if (wagonType.equals("cargo")) {
            // Maak een cargowagon
            return trainFacade.insertComponent(new ComponentBuilder(wagonName).withCargo(amount).build()) ?
                    "wagon " + wagonName + " with " + amount + " amount of cargo created" : "wagon " + wagonName + " already exists.";
        }

        return "did not succeed in creating a wagon";
    }

    private boolean componentExists(String type) {
        for (String s : trainFacade.getComponentTypes())
            if (s.equalsIgnoreCase(type))
                return true;
        return false;
    }

}