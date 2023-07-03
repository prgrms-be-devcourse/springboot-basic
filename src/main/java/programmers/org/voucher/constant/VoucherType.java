package programmers.org.voucher.constant;

import java.util.Arrays;
import java.util.Optional;

public enum VoucherType {
    FIXED,
    PERCENT;

    public static Optional<VoucherType> find(String type) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.name().equals(type))
                .findAny();
    }
}
