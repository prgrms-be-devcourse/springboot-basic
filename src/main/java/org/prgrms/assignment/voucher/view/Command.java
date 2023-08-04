package org.prgrms.assignment.voucher.view;

public class Command {

    private static final String LongWordError = "Your word length is over than 1!";
    private final String command;

    public Command(String command) {
        if(isLongerThanOne(command)) {
            throw new IllegalArgumentException(LongWordError);
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
