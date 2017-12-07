package domain.command;

public class NoneCommand extends Command {

    public NoneCommand() {
        super("", 0, "");
    }

    @Override
    public String execute(String[] args) {
        return "No domain.command found for input: " + convertArgsToString(args) + ".";
    }

}
