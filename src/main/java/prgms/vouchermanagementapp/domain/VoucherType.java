package prgms.vouchermanagementapp.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum VoucherType {
    FixedAmountVoucher(1),
    PercentDiscountVoucher(2);

    private final int index;

    VoucherType(int index) {
        this.index = index;
    }

    public static List<VoucherType> getValues() {
        return List.of(VoucherType.values());
    }

    public static Optional<VoucherType> of(String index) throws IllegalArgumentException {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> index.equals(String.valueOf(voucherType.index)))
                .findFirst();
    }

    public boolean is(VoucherType voucherType) {
        return this == voucherType;
    }

    public int getIndex() {
        return this.index;
    }
}
