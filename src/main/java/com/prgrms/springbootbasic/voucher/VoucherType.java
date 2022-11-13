package com.prgrms.springbootbasic.voucher;

import com.prgrms.springbootbasic.common.exception.InvalidVoucherTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Arrays;

public enum VoucherType {
    FIXED_AMOUNT("fixed amount"),
    PERCENT("percent");

    private static final Logger logger = LoggerFactory.getLogger(VoucherType.class);

    private final String inputValue;

    VoucherType(String inputValue) {
        this.inputValue = inputValue;
    }

    public String getInputValue() {
        return inputValue;
    }

    /**
     * @throws InvalidVoucherTypeException VoucherType에 해당하지 않는 입력이 주어졌을 때 발생하는 예외입니다.
     */
    public static VoucherType from(String inputValue) {
        return Arrays.stream(values())
                .filter(voucherType -> voucherType.getInputValue().equals(inputValue))
                .findFirst()
                .orElseThrow(() -> {
                    logger.warn("InvalidVoucherTypeException occurred when getting voucher type from console. Invalid voucher type input was provided");
                    return new InvalidVoucherTypeException(
                            MessageFormat.format("Input voucher {0} is invalid. Please select again.", inputValue));
                });
    }
}
