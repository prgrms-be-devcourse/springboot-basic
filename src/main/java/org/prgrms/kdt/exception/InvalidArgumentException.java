package org.prgrms.kdt.exception;

public class InvalidArgumentException extends RuntimeException {
    public enum ErrorMessage {
        NOT_CORRECT_INPUT_MESSAGE("Not The Correct Message");

        ErrorMessage(String s) {
        }
    }

    public InvalidArgumentException(ErrorMessage message) {
        super(message.name());
    }
}
