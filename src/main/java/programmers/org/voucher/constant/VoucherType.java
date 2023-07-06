package programmers.org.voucher.constant;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static programmers.org.voucher.exception.ErrorMessage.VOUCHER_ERROR_MESSAGE;

public enum VoucherType {
    FIXED,
    PERCENT;

    public static VoucherType find(String type) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.name().equals(type))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(VOUCHER_ERROR_MESSAGE.getMessage()));
    }
}
