package domain.command.subcommands;

import domain.command.Command;

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
