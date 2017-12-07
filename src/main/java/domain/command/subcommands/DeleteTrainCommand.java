package domain.command.subcommands;

import domain.command.Command;

public class DeleteTrainCommand extends Command {

    public DeleteTrainCommand() {
        super("delete train", "delete", "train", "type");
    }

    @Override
    public String execute(String[] args) {
        //TODO:
        return "train test deleted";
    }

}
