package com.prgrms.springbootbasic.voucher;

import com.prgrms.springbootbasic.common.exception.InvalidVoucherTypeException;
import com.prgrms.springbootbasic.voucher.domain.FixedAmountVoucher;
import com.prgrms.springbootbasic.voucher.domain.PercentVoucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Arrays;

public enum VoucherType {
    FIXED_AMOUNT("fixedAmount", FixedAmountVoucher.class.getSimpleName()),
    PERCENT("percent", PercentVoucher.class.getSimpleName());
    private static final Logger logger = LoggerFactory.getLogger(VoucherType.class);

    private final String inputValue;
    private final String className;

    VoucherType(String inputValue, String className) {
        this.inputValue = inputValue;
        this.className = className;
    }

    public String getInputValue() {
        return inputValue;
    }

    /**
     * @throws InvalidVoucherTypeException VoucherType에 해당하지 않는 입력이 주어졌을 때 발생하는 예외입니다.
     */
    public static VoucherType fromInputValue(String inputValue) {
        return Arrays.stream(values())
                .filter(voucherType -> voucherType.getInputValue().equals(inputValue))
                .findFirst()
                .orElseThrow(() -> {
                    logger.warn("InvalidVoucherTypeException occurred when getting voucher type from console. Invalid voucher type input was provided");
                    return new InvalidVoucherTypeException(
                            MessageFormat.format("Input voucher {0} is invalid. Please select again.", inputValue));
                });
    }

    public static VoucherType fromClassName(String className){
        return Arrays.stream(values())
            .filter(voucherType -> voucherType.className.equals(className))
            .findFirst()
            .orElseThrow(() -> {
                logger.warn("InvalidVoucherTypeException occurred when getting voucher type from console. Invalid voucher type input was provided");
                return new InvalidVoucherTypeException(
                    MessageFormat.format("class name {0} doesn" + "'" + "t match.", className));
            });
    }
}
