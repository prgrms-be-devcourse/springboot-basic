package prgms.vouchermanagementapp.voucher;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum VoucherType {
    FixedAmountVoucher(1),
    PercentDiscountVoucher(2);

    private final int index;

    VoucherType(int index) {
        this.index = index;
    }

    public static List<VoucherType> getValues() {
        return Arrays.stream(VoucherType.values())
                .collect(Collectors.toList());
    }

    public int getIndex() {
        return this.index;
    }
}
