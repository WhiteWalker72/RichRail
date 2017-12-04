package command;

public abstract class Command {

    private final String identifier;
    private final int minimalLength;
    private final String[] args;

    public Command(String identifier, int minimalLength, String... args) {
        this.identifier = identifier;
        this.minimalLength = minimalLength;
        this.args = args;
    }

    public Command(String identifier, String... args) {
        this(identifier, args.length, args);
    }

    String getIdentifier() {
        return identifier;
    }

    int getMinimalLength() {
        return minimalLength;
    }

    String[] getArgs() {
        return args;
    }

    public abstract String execute(String[] args);

    public String convertArgsToString() {
        return convertArgsToString(args);
    }

    public String convertArgsToString(String[] args) {
        String result = "";
        for (int i = 0; i < args.length; i++) {
            result += i < args.length - 1 ? args[i] + " " : args[i];
        }
        return result;
    }

}
