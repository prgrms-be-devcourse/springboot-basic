package org.prgrms.kdt.io;

public class Validator {
    public static final String EXIT = "exit";
    public static final String LIST = "list";
    public static final String CREATE = "create";

    public boolean isInValidCommand(String line) {
        return !line.equals(EXIT) && !line.equals(LIST) && !line.equals(CREATE);
    }

    public boolean isInValidVoucherTypeAmount(String[] line) {
        if (isEmpty(line))
            return true;

        for (String str : line) {
            for (int i = 0; i < str.length(); i++) {
                if (isNotDigit(str.charAt(i)))
                    return true;
            }
        }

        int type = mapToInt(line[0]);
        long value = mapToLong(line[1]);

        if (isInvalidType(type))
            return true;

        if (isUnderZero(value))
            return true;

        return false;
    }

    private boolean isUnderZero(long value) {
        return value < 0;
    }

    private boolean isInvalidType(int type) {
        return type != 0 && type != 1;
    }

    private long mapToLong(String s) {
        return Long.parseLong(s);
    }

    private int mapToInt(String s) {
        return Integer.parseInt(s);
    }

    private boolean isNotDigit(char ch) {
        return !Character.isDigit(ch);
    }

    private boolean isEmpty(String[] line) {
        return line.length != 2;
    }
}
