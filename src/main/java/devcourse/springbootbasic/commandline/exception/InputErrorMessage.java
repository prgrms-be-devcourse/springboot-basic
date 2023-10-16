package devcourse.springbootbasic.commandline.exception;

import lombok.Getter;

@Getter
public enum InputErrorMessage {

    NOT_NUMBER("Input is not a number. Please try again.");

    private final String message;

    InputErrorMessage(String message) {
        this.message = message;
    }
}
