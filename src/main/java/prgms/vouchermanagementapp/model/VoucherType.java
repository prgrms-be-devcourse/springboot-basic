package prgms.vouchermanagementapp.model;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER(1, "FixedAmountVoucher", "fixed"),
    PERCENT_DISCOUNT_VOUCHER(2, "PercentDiscountVoucher", "percent");

    private final int index;
    private final String specificType;
    private final String simpleType;

    VoucherType(int index, String specificType, String simpleType) {
        this.index = index;
        this.specificType = specificType;
        this.simpleType = simpleType;
    }

    public static List<VoucherType> getValues() {
        return List.of(VoucherType.values());
    }

    public static Optional<VoucherType> of(String index) throws IllegalArgumentException {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> index.equals(String.valueOf(voucherType.index)))
                .findFirst();
    }

    public static VoucherType fromSpecificType(String typeLiteral) {
        return getValues().stream()
                .filter(voucherType -> voucherType.getSpecificType().equals(typeLiteral))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                                MessageFormat.format(
                                        "No Matching Voucher Type exists for typeLiteral ''{0}''", typeLiteral
                                )
                        )
                );
    }

    public static VoucherType fromSimpleType(String simpleType) {
        return getValues().stream()
                .filter(voucherType -> voucherType.getSimpleType().equals(simpleType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                                MessageFormat.format(
                                        "No Matching Voucher Type exists for simpleType ''{0}''", simpleType
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

    public String getSpecificType() {
        return this.specificType;
    }

    public String getSimpleType() {
        return this.simpleType;
    }
}
