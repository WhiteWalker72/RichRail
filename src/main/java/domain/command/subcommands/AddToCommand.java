package domain.command.subcommands;

import domain.command.Command;

public class AddToCommand extends Command {

    public AddToCommand() {
        super("add", "add", "wagon", "to", "train");
    }

    @Override
    public String execute(String[] args) {
        //TODO:
        return "wagon testwagon added to testtrain";
    }

}
