package prgms.vouchermanagementapp.exception;

import java.text.MessageFormat;

public class IllegalVoucherTypeIndexException extends IllegalArgumentException {

    public static final String MESSAGE_FORMAT = "Input index ''{0}'' is invalid. Please retry.";

    public IllegalVoucherTypeIndexException(String index) {
        super(MessageFormat.format(MESSAGE_FORMAT, index));
    }
}
