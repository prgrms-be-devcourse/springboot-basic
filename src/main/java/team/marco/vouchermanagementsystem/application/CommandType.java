package team.marco.vouchermanagementsystem.application;

import static java.text.MessageFormat.format;

public enum CommandType {
    CREATE, LIST, EXIT, BLACKLIST;

    public static CommandType getCommandType(String input) {
        try {
            return valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(format("{0}: 사용할 수 없는 명령어입니다.", input));
        }
    }
}
