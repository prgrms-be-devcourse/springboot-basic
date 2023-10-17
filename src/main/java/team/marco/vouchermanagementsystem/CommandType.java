package team.marco.vouchermanagementsystem;

import java.text.MessageFormat;
import java.util.Arrays;

public enum CommandType {
    CREATE, LIST, EXIT;

    public static CommandType getCommandType(String input) {
        return Arrays.stream(CommandType.values())
                .filter(commandType -> commandType.isMatch(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("{0}: 사용할 수 없는 명령어입니다.", input)));
    }

    private boolean isMatch(String input) {
        return this.name().toLowerCase().equals(input);
    }
}
