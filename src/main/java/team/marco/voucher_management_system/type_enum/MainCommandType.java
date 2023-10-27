package team.marco.voucher_management_system.type_enum;

import static java.text.MessageFormat.format;

public enum MainCommandType {
    CREATE, LIST, EXIT, BLACKLIST, CUSTOMER;

    public static MainCommandType getCommandType(String input) {
        try {
            return valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(format("{0}: 사용할 수 없는 명령어입니다.", input));
        }
    }
}
