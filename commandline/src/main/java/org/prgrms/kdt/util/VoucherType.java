package org.prgrms.kdt.util;

import org.prgrms.kdt.domain.FixedAmountVoucher;
import org.prgrms.kdt.domain.PercentDiscountVoucher;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.exception.ErrorCode;
import org.prgrms.kdt.exception.NotFoundVoucherTypeException;

import java.util.UUID;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED_AMOUNT("1", "FixedAmountVoucher", "amount", (uuid, discountDegree) -> new FixedAmountVoucher(uuid, discountDegree)),
    PERCENTAGE("2", "PercentDiscountVoucher", "percent", (uuid, discountDegree) -> new PercentDiscountVoucher(uuid, discountDegree));

    private final String typeValue;
    private final String name;
    private final String discountType;
    private final BiFunction<UUID, Long, Voucher> voucherBiFunction;

    private static final int NOT_FOUND_RESULT = -1;
    private static final String EQUAL = "=";

    private Voucher create(UUID uuid, long discountDegree) {
        return this.voucherBiFunction.apply(uuid, discountDegree);
    }

    VoucherType(String typeValue, String name, String discountType, BiFunction<UUID, Long, Voucher> voucherBiFunction) {
        this.typeValue = typeValue;
        this.name = name;
        this.discountType = discountType;
        this.voucherBiFunction = voucherBiFunction;
    }

    public static Voucher createVoucher(String type, UUID voucherId, long discountDegree) {
        VoucherType selectVoucherType = VoucherType.selectVoucherType(type);
        return selectVoucherType.create(voucherId, discountDegree);
    }

    private static VoucherType selectVoucherType(String type) {
        return Stream.of(values())
                .filter(voucherType -> voucherType.typeValue.equals(type))
                .findFirst()
                .orElseThrow(() -> new NotFoundVoucherTypeException(ErrorCode.NOT_FOUND_VOUCHER_TYPE_EXCEPTION.getMessage()));
    }

    private static boolean isContainType(String type) {
        return Stream.of(values())
                .anyMatch(voucherType -> voucherType.typeValue.equals(type));
    }

    public static void checkType(String type) {
        if (!isContainType(type)) {
            throw new NotFoundVoucherTypeException(ErrorCode.NOT_FOUND_VOUCHER_TYPE_EXCEPTION.getMessage());
        }
    }

    public static VoucherType selectVoucherTypeFromFile(String type) {
        return Stream.of(values())
                .filter(voucherType -> type.indexOf(voucherType.name) != NOT_FOUND_RESULT)
                .findFirst()
                .orElseThrow(() -> new NotFoundVoucherTypeException(ErrorCode.NOT_FOUND_VOUCHER_TYPE_EXCEPTION.getMessage()));
    }

    public static Voucher createVoucherFromFile(VoucherType voucherType, String stringId, String stringDiscount) {
        UUID voucherId = UUID.fromString(stringId.replace("voucherId=", ""));
        Long discountDegree = Long.parseLong(stringDiscount.replace(voucherType.discountType + EQUAL, "").trim());
        return voucherType.create(voucherId, discountDegree);
    }
}
