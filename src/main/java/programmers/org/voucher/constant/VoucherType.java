package programmers.org.voucher.constant;

import java.util.Arrays;
import java.util.Optional;

public enum VoucherType {
    FIXED("fixed"),
    PERCENT("percent");

    private final String type;

    VoucherType(String type) {
        this.type = type;
    }

    public static Optional<VoucherType> find(String type) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.type.equals(type))
                .findAny();
    }
}
