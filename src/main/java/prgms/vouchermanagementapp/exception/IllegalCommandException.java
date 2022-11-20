package prgms.vouchermanagementapp.exception;

import java.text.MessageFormat;

public class IllegalCommandException extends IllegalArgumentException {

    private static final String MESSAGE_FORMAT = "command ''{0}'' is invalid. Please Retry.";

    public IllegalCommandException(String command) {
        super(MessageFormat.format(MESSAGE_FORMAT, command));
    }
}
