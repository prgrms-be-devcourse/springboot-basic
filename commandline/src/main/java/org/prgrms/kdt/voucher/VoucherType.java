package org.prgrms.kdt.voucher;

import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.exception.ErrorCode;
import org.prgrms.kdt.exception.NotFoundVoucherTypeException;

import java.util.function.BiFunction;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED_AMOUNT("1", "FixedAmountVoucher", (voucherId, discountDegree) -> new FixedAmountVoucher(voucherId, discountDegree)),
    PERCENTAGE("2", "PercentDiscountVoucher", (voucherId, discountDegree) -> new PercentDiscountVoucher(voucherId, discountDegree));

    private final String typeValue;
    private final String typeName;
    private final BiFunction<Long, Long, Voucher> voucherBiFunction;

    private static final int NOT_FOUND_RESULT = -1;

    private Voucher create(Long voucherId, long discountDegree) {
        return this.voucherBiFunction.apply(voucherId, discountDegree);
    }

    VoucherType(String typeValue, String typeName, BiFunction<Long, Long, Voucher> voucherBiFunction) {
        this.typeValue = typeValue;
        this.typeName = typeName;
        this.voucherBiFunction = voucherBiFunction;
    }

    public static String getTypeName(String typeValue) {
        VoucherType voucherType = selectVoucherTypeByTypeNumber(typeValue);
        return voucherType.typeName;
    }

    public static Voucher createVoucher(String typeNumber, long voucherId, long discountDegree) {
        VoucherType selectVoucherType = VoucherType.selectVoucherTypeByTypeNumber(typeNumber);
        return selectVoucherType.create(voucherId, discountDegree);
    }

    public static Voucher createVoucher(VoucherType voucherType, long voucherId, long discountDegree) {
        return voucherType.create(voucherId, discountDegree);
    }

    private static VoucherType selectVoucherTypeByTypeNumber(String typeNumber) {
        return Stream.of(values())
                .filter(voucherType -> voucherType.typeValue.equals(typeNumber))
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

    public static VoucherType selectVoucherTypeFromTypeName(String type) {
        return Stream.of(values())
                .filter(voucherType -> type.indexOf(voucherType.typeName) != NOT_FOUND_RESULT)
                .findFirst()
                .orElseThrow(() -> new NotFoundVoucherTypeException(ErrorCode.NOT_FOUND_VOUCHER_TYPE_EXCEPTION.getMessage()));
    }

    public static String getVoucherTypeName(String typeNumber) {
        VoucherType type = Stream.of(VoucherType.values())
                .filter(voucherType -> voucherType.typeValue.equals(typeNumber))
                .findFirst()
                .orElseThrow(() -> new NotFoundVoucherTypeException(ErrorCode.NOT_FOUND_VOUCHER_EXCEPTION.getMessage()));

        return type.typeName;
    }
}
