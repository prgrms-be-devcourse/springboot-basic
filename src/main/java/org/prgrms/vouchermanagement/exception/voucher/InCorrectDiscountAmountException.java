package org.prgrms.vouchermanagement.exception.voucher;

import org.prgrms.vouchermanagement.voucher.domain.VoucherType;

import java.util.Arrays;

public class InCorrectDiscountAmountException extends RuntimeException{

    private static final String SEPARATOR = " : 0 ~ ";

    public InCorrectDiscountAmountException() {
        super("정상 범위의 할인값을 입력해주세요."
                + System.lineSeparator()
                + getVoucherCorrectRangeInfo());
    }

    private static String getVoucherCorrectRangeInfo() {
        return Arrays.stream(VoucherType.values())
                .map(voucherType -> voucherType.name() + SEPARATOR + voucherType.getMaximumDiscountAmount())
                .reduce("", (init, voucherInfo) -> init + voucherInfo + System.lineSeparator());
    }
}
