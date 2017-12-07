package domain.command;

import domain.command.subcommands.*;
import utils.option.IOption;
import utils.option.None;
import utils.option.Some;

import java.util.HashSet;
import java.util.Set;

public class CommandManager {

    private static CommandManager instance;
    private final Set<Command> commands = new HashSet<>();

    private CommandManager() {
        registerCommand(new AddToCommand());
        registerCommand(new DeleteTrainCommand());
        registerCommand(new DeleteWagonCommand());
        registerCommand(new GetTrainSeatsCommand());
        registerCommand(new GetWagonSeatsCommand());
        registerCommand(new NewTrainCommand());
        registerCommand(new NewWagonCommand());
        registerCommand(new RemoveFromCommand());
    }

    private void registerCommand(Command command) {
        commands.add(command);
    }

    private IOption<Command> getCommand(String input) {
        for (Command command : commands)
            if (input.toLowerCase().startsWith(command.getIdentifier().toLowerCase()))
                return new Some<>(command);
        return new None<>();
    }

    public String execute(String input) {
        String[] args = input.split("\\s+");
        Command command = getCommand(input).visit(() -> new NoneCommand(), x -> x);

        return args.length < command.getMinimalLength()
                ? "Not enough arguments found for domain.command: " + command.convertArgsToString() + "."
                : command.execute(args);
    }

    public static CommandManager getInstance() {
        if (instance == null)
            instance = new CommandManager();
        return instance;
    }

}
