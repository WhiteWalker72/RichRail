package command.subcommands;

import command.Command;

public class NewTrainCommand extends Command {

    public NewTrainCommand() {
        super("new train", "new", "train", "name");
    }

    @Override
    public String execute(String[] args) {
        //TODO:
        return "train test created";
    }

}
