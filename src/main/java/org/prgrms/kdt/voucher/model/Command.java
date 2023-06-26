package org.prgrms.kdt.voucher.model;

public class Command {
    private static final String LongWordError = "Your word length is over than 1!";
    private static final String WrongMenuError = "There is no menu named ";
    private final String command;

    public Command(String command) {
        if(isLongerThanOne(command)) {
            throw new IllegalArgumentException(LongWordError);
        }
        if(!Menu.isValidMenu(command)) {
            throw new IllegalArgumentException(WrongMenuError+command);
        }
        this.command = command;
    }

    private boolean isLongerThanOne(String command) {
        String[] parsedCommand = command.
                split(" ");
        return parsedCommand.length >= 2;
    }

    public String getCommand() {
        return command;
    }
}
