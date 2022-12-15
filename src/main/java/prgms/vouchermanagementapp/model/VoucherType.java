package prgms.vouchermanagementapp.model;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER(1, "FixedAmountVoucher"),
    PERCENT_DISCOUNT_VOUCHER(2, "PercentDiscountVoucher");

    private final int index;
    private final String typeLiteral;

    VoucherType(int index, String typeLiteral) {
        this.index = index;
        this.typeLiteral = typeLiteral;
    }

    public static List<VoucherType> getValues() {
        return List.of(VoucherType.values());
    }

    public static Optional<VoucherType> of(String index) throws IllegalArgumentException {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> index.equals(String.valueOf(voucherType.index)))
                .findFirst();
    }

    public static VoucherType from(String typeLiteral) {
        return getValues().stream()
                .filter(voucherType -> voucherType.getTypeLiteral().equals(typeLiteral))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                                MessageFormat.format(
                                        "No Matching Voucher Type exists for typeLiteral ''{0}''", typeLiteral
                                )
                        )
                );
    }

    public boolean is(VoucherType voucherType) {
        return this == voucherType;
    }

    public int getIndex() {
        return this.index;
    }

    public String getTypeLiteral() {
        return this.typeLiteral;
    }
}
