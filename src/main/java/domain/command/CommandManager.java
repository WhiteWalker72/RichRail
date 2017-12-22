package domain.command;

import org.reflections.Reflections;
import utils.option.IOption;
import utils.option.None;
import utils.option.Some;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

public class CommandManager {

    private static volatile CommandManager instance;
    private final Set<Command> commands = new HashSet<>();

    private CommandManager() {
        Reflections reflections = new Reflections("domain.command.subcommands");
        Set<Class<? extends Command>> commands = reflections.getSubTypesOf(Command.class);
        for (Class<? extends Command> command : commands) {
            try {
                registerCommand(command.getConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
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
        Command command = getCommand(input).visit(NoneCommand::new, x -> x);

        return args.length < command.getMinimalLength()
                ? "Not enough arguments found for command: " + command.convertArgsToString() + "."
                : command.execute(args);
    }

    public static CommandManager getInstance() {
        if (instance == null) {
            synchronized (CommandManager.class) {
                if (instance == null)
                    instance = new CommandManager();
            }
        }
        return instance;
    }

}
