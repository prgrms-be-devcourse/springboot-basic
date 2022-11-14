package org.prgrms.kdt.util;

import org.prgrms.kdt.domain.FixedAmountVoucher;
import org.prgrms.kdt.domain.PercentDiscountVoucher;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.exception.ErrorCode;
import org.prgrms.kdt.exception.NotFoundVoucherTypeException;

import java.util.UUID;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED_AMOUNT("1"),
    PERCENTAGE("2");

    private final String typeValue;

    VoucherType(String typeValue) {
        this.typeValue = typeValue;
    }

    public static Voucher createVoucher(String type, long discountDegree) {
        VoucherType voucherType = VoucherType.selectVoucherType(type);
        return switch (voucherType) {
            case FIXED_AMOUNT -> new FixedAmountVoucher(UUID.randomUUID(), discountDegree);
            case PERCENTAGE -> new PercentDiscountVoucher(UUID.randomUUID(), discountDegree);
            default -> throw new NotFoundVoucherTypeException(ErrorCode.NOT_FOUND_VOUCHER_TYPE_EXCEPTION.getMessage());
        };
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
}
