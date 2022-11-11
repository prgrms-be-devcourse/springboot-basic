package com.programmers.VoucherManagementApplication.voucher;

import com.programmers.VoucherManagementApplication.io.Message;

import java.util.Arrays;
import java.util.function.BiFunction;

import static com.programmers.VoucherManagementApplication.io.Message.*;

public enum VoucherType {

    FIXED_AMOUNT("FIXEDAMOUNTVOUCHER", FIXED_VOUCHER_INPUT_PROMPT, (originPrice, amount) -> originPrice - amount),
    DISCOUNT_PERCENT("PERCENTDISCOUNTVOUCHER", PERCENT_VOUCHER_INPUT_PROMPT, (originPrice, amount) -> originPrice - (originPrice * amount) / 100);

    private final String inputValue;
    private final Message inputMessage;
    private final BiFunction<Long, Long, Long> arithmetic;

    VoucherType(String inputValue, Message inputMessage, BiFunction<Long, Long, Long> arithmetic) {
        this.inputValue = inputValue;
        this.inputMessage = inputMessage;
        this.arithmetic = arithmetic;
    }

    public Message getInputMessage() {
        return inputMessage;
    }

    public BiFunction<Long, Long, Long> getArithmetic() {
        return this.arithmetic;
    }

    public static VoucherType of(String inputValue) {
        VoucherType voucherType = Arrays.stream(values())
                .filter(discountType -> discountType.inputValue.equals(inputValue))
                .findAny()
                .orElseThrow(() -> { throw new IllegalArgumentException(String.valueOf(INVALID_INPUT)); });
        return voucherType;
    }
}
