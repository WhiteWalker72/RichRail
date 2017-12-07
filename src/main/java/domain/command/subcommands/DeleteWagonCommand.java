package domain.command.subcommands;

import domain.command.Command;

public class DeleteWagonCommand extends Command {

    public DeleteWagonCommand() {
        super("delete wagon",  "delete", "wagon", "type");
    }

    @Override
    public String execute(String[] args) {
        //TODO:
        return "wagon test deleted";
    }

}
