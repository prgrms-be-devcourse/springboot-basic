package prgms.vouchermanagementapp.domain;

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

    public static String toTypeLiteral(String voucherTypeLiteral) {
        return Arrays.stream(VoucherType.values())
                .map(voucherType -> voucherType.typeLiteral)
                .filter(typeLiteral -> typeLiteral.equals(voucherTypeLiteral))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        MessageFormat.format("voucherType ''{0}'' doesn't exist!", voucherTypeLiteral))
                );
    }

    public boolean is(VoucherType voucherType) {
        return this == voucherType;
    }

    public int getIndex() {
        return this.index;
    }
}
