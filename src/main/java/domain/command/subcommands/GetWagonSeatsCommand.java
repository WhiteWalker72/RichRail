package domain.command.subcommands;

import domain.command.Command;

public class GetWagonSeatsCommand extends Command {

    public GetWagonSeatsCommand() {
        super("getnumseats wagon", "getnumseats", "wagon", "name");
    }

    @Override
    public String execute(String[] args) {
        //TODO:
        return "number of seats in wagon test: 0";
    }
}
