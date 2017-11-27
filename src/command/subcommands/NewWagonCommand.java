package command.subcommands;

import command.Command;

public class NewWagonCommand extends Command {

    public NewWagonCommand() {
        super("new wagon", "new", "wagon", "name");
    }

    @Override
    public String execute(String[] args) {
        //TODO:
        return "wagon test created";
    }

}
