package org.prgrms.kdt.util;

import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.exception.ErrorCode;
import org.prgrms.kdt.exception.NotFoundVoucherTypeException;

import java.util.stream.Stream;

public enum VoucherType {
    FIXED_AMOUNT("1", "FixedAmountVoucher", "amount", (uuid, typeName, discountDegree) -> new FixedAmountVoucher(uuid, typeName, discountDegree)),
    PERCENTAGE("2", "PercentDiscountVoucher", "percent", (uuid, typeName, discountDegree) -> new PercentDiscountVoucher(uuid, typeName, discountDegree));

    private final String typeValue;
    private final String typeName;
    private final String discountType;
    private final TriFunction<Long, String, Long, Voucher> voucherBiFunction;

    private static final int NOT_FOUND_RESULT = -1;

    private Voucher create(Long uuid, long discountDegree) {
        return this.voucherBiFunction.apply(uuid, this.typeName, discountDegree);
    }

    public String getDiscountType() {
        return discountType;
    }

    VoucherType(String typeValue, String typeName, String discountType, TriFunction<Long, String, Long, Voucher> voucherBiFunction) {
        this.typeValue = typeValue;
        this.typeName = typeName;
        this.discountType = discountType;
        this.voucherBiFunction = voucherBiFunction;
    }

    public static Voucher createVoucher(String typeNumber, long voucherId, long discountDegree) {
        VoucherType selectVoucherType = VoucherType.selectVoucherTypeByTypeNumber(typeNumber);
        return selectVoucherType.create(voucherId, discountDegree);
    }

    public static Voucher createVoucher(VoucherType voucherType, long voucherId, long discountDegree) {
        return voucherType.create(voucherId, discountDegree);
    }

    public static String getVoucherTypeName(String type) {
        VoucherType voucherType = selectVoucherTypeByTypeNumber(type);
        return voucherType.typeName;
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

}
