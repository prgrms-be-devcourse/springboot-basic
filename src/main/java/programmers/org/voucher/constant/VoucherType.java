package programmers.org.voucher.constant;

import java.util.Arrays;
import java.util.Optional;

public enum VoucherType {
    FIXED("fixed"), PERCENT("percent");

    private final String voucherTypeString;

    VoucherType(String voucherTypeString) {
        this.voucherTypeString = voucherTypeString;
    }

    public static Optional<VoucherType> find(String voucherTypeString) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.voucherTypeString.equals(voucherTypeString))
                .findAny();
    }
}
