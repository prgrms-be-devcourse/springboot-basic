package devcourse.springbootbasic.commandline.console.constant;

import lombok.Getter;

@Getter
public enum InputMessage {

    FEATURE_SELECTION("Select the features you want to use."),
    VOUCHER_TYPE("Select voucher type: "),
    DISCOUNT_VALUE("Enter discount value: ");

    private final String message;

    InputMessage(String message) {
        this.message = message;
    }
}
