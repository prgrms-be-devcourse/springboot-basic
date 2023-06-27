package kr.co.programmers.school.voucher.domain.voucher.dto;

import kr.co.programmers.school.voucher.domain.voucher.enums.VoucherType;
import kr.co.programmers.school.voucher.domain.voucher.exception.ApplicationException;
import kr.co.programmers.school.voucher.domain.voucher.exception.ErrorMessage;

import java.util.regex.Pattern;

public class VoucherRequest {
    private static final Pattern PERCENT_INPUT_RANGE_PATTERN = Pattern.compile("^[1-9][0-9]?$|^100$");
    private static final Pattern FIXED_INPUT_PATTERN = Pattern.compile("^[1-9]\\d*$");

    private final int amount;
    private final VoucherType voucherType;

    public VoucherRequest(int amount, VoucherType voucherType) {
        this.amount = amount;
        this.voucherType = voucherType;
    }

    public static VoucherRequest of(String amount, VoucherType voucherType) {
        if (!validate(amount, voucherType)) {
            throw new ApplicationException(ErrorMessage.WRONG_RANGE);
        }
        return new VoucherRequest(Integer.parseInt(amount), voucherType);
    }

    private static boolean validate(String amount, VoucherType voucherType) {
        if (voucherType.isPercentAmount()) {
            return PERCENT_INPUT_RANGE_PATTERN.matcher(amount).matches();
        }

        return FIXED_INPUT_PATTERN.matcher(amount).matches();
    }

    public VoucherType getVoucherType() {
        return this.voucherType;
    }

    public int getAmount() {
        return this.amount;
    }

    public String toString() {
        return this.voucherType + " " + this.amount;
    }
}