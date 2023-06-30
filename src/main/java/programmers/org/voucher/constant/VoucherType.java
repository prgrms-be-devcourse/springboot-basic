package programmers.org.voucher.constant;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static programmers.org.voucher.exception.ErrorMessage.VOUCHER_ERROR_MESSAGE;

public enum VoucherType {
    FIXED("fixed"),
    PERCENT("percent");

    private final String content;

    VoucherType(String content) {
        this.content = content;
    }

    public static VoucherType find(String content) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.content.equals(content))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(VOUCHER_ERROR_MESSAGE.getMessage()));
    }
}
