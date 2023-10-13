package team.marco.vouchermanagementsystem;

import java.util.Arrays;

public enum CommandType {
    CREATE, LIST, EXIT;

    public static CommandType getCommandType(String input) {
        return Arrays.stream(CommandType.values())
                .filter(commandType -> commandType.isMatch(input))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    private boolean isMatch(String input) {
        return this.name().toLowerCase().equals(input);
    }
}
