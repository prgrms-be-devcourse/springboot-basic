package devcourse.springbootbasic.exception;

import lombok.Getter;

@Getter
public enum CustomerErrorMessage {

    NOT_FOUND("Not exist customer id");

    private final String message;

    CustomerErrorMessage(String message) {
        this.message = message;
    }
}
