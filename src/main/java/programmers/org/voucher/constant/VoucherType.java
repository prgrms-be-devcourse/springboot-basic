package programmers.org.voucher.constant;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static programmers.org.voucher.exception.ErrorMessage.VOUCHER_ERROR_MESSAGE;

public enum VoucherType {
    FIXED("fixed"),
    PERCENT("percent");

    private final String type;

    VoucherType(String type) {
        this.type = type;
    }

    public static VoucherType find(String type) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.type.equals(type))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(VOUCHER_ERROR_MESSAGE.getMessage()));
    }
}
