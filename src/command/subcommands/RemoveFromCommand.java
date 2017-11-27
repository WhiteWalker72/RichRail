package command.subcommands;

import command.Command;

public class RemoveFromCommand extends Command {

    public RemoveFromCommand() {
        super("remove", "remove", "wagon", "from", "train");
    }

    @Override
    public String execute(String[] args) {
        //TODO:
        return "wagon testwagon removed from train testtrain";
    }

}
