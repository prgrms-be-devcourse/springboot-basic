package devcourse.springbootbasic.commandline.constant;

import lombok.Getter;

@Getter
public enum InputMessage {

    FEATURE_SELECTION("Select the features you want to use."),
    VOUCHER_TYPE("Select voucher type: "),
    DISCOUNT_VALUE("Enter discount value: "),
    CUSTOMER_NAME("Enter customer name: "),
    CUSTOMER_ID("Enter customer id: "),
    BLACKLIST_STATUS("Enter blacklist status (y/n): "),
    VOUCHER_ID("Enter voucher id: ");

    private final String message;

    InputMessage(String message) {
        this.message = message;
    }
}
