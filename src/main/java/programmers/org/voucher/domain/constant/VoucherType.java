package programmers.org.voucher.domain.constant;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static programmers.org.voucher.exception.ErrorMessage.INVALID_VOUCHER_TYPE;

public enum VoucherType {
    FIXED,
    PERCENT
    ;

    public static VoucherType find(String type) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.name().equalsIgnoreCase(type))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(INVALID_VOUCHER_TYPE.getMessage()));
    }
}
