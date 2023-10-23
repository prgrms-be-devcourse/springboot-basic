package devcourse.springbootbasic.exception;

import lombok.Getter;

@Getter
public enum InputErrorMessage {

    INVALID_COMMAND("Invalid command. Please try again."),
    NOT_NUMBER("Input is not a number. Please try again."),
    INVALID_VOUCHER_TYPE("Invalid voucher type. Please try again.");

    private final String message;

    InputErrorMessage(String message) {
        this.message = message;
    }
}
