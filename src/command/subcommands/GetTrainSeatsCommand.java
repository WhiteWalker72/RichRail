package command.subcommands;

import command.Command;

public class GetTrainSeatsCommand extends Command {

    public GetTrainSeatsCommand() {
        super("getnumseats train", "getnumseats", "train", "name");
    }

    @Override
    public String execute(String[] args) {
        //TODO:
        return "number of seats in train test: 0";
    }

}
