package devcourse.springbootbasic.exception;

import lombok.Getter;

@Getter
public enum VoucherErrorMessage {

    INVALID_DISCOUNT_VALUE("Invalid discount value. Please try again."),
    NOT_FOUND("Not exist voucher id"),
    NOT_ASSIGNED("Voucher is not assigned to any customer");

    private final String message;

    VoucherErrorMessage(String message) {
        this.message = message;
    }
}
