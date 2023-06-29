package programmers.org.voucher.constant;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static programmers.org.voucher.exception.ErrorMessage.VOUCHER_ERROR_MESSAGE;

public enum VoucherType {
    FIXED("fixed"), PERCENT("percent");

    private final String voucherTypeString;

    VoucherType(String voucherTypeString) {
        this.voucherTypeString = voucherTypeString;
    }

    public static VoucherType find(String voucherTypeString) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.voucherTypeString.equals(voucherTypeString))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(VOUCHER_ERROR_MESSAGE.getMessage()));
    }
}
