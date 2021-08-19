package org.prgrms.kdt.io;

public class Validator {
    public static final String EXIT = "exit";
    public static final String LIST = "list";
    public static final String CREATE = "create";

    public boolean isInValidCommand(String line) {
        return !line.equals(EXIT) && !line.equals(LIST) && !line.equals(CREATE);
    }

    public boolean isInValidVoucherTypeAmount(String[] line) {
        if (line.length != 2)
            return true;

        for (String str : line) {
            for (int i = 0; i < str.length(); i++) {
                if (!Character.isDigit(str.charAt(i)))
                    return true;
            }
        }

        int type = Integer.parseInt(line[0]);
        long amount = Long.parseLong(line[1]);

        if (type != 0 && type != 1)
            return true;

        if (amount < 0)
            return true;

        return false;
    }
}
