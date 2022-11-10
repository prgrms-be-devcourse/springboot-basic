package prgms.vouchermanagementapp.voucher;

import java.util.Arrays;
import java.util.List;

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

    public static VoucherType of(String index) throws IllegalArgumentException {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> index.equals(String.valueOf(voucherType.index)))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public boolean is(VoucherType voucherType) {
        return this == voucherType;
    }

    public int getIndex() {
        return this.index;
    }
}
