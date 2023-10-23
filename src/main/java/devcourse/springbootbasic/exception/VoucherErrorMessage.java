package devcourse.springbootbasic.exception;

import lombok.Getter;

@Getter
public enum VoucherErrorMessage {

    INVALID_DISCOUNT_VALUE("Invalid discount value. Please try again.");

    private final String message;

    VoucherErrorMessage(String message) {
        this.message = message;
    }
}
