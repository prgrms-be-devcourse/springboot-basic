package org.prgrms.kdt.util;

import org.prgrms.kdt.domain.FixedAmountVoucher;
import org.prgrms.kdt.domain.PercentDiscountVoucher;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.exception.ErrorCode;
import org.prgrms.kdt.exception.NotFoundVoucherTypeException;

import java.util.UUID;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED_AMOUNT("1", "FixedAmountVoucher"),
    PERCENTAGE("2", "PercentDiscountVoucher");

    private final String typeValue;
    private final String name;

    private static final int NOT_FOUND_RESULT = -1;

    VoucherType(String typeValue, String name) {
        this.typeValue = typeValue;
        this.name = name;
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

    public static VoucherType selectVoucherTypeFromFile(String type) {
        return Stream.of(values())
                .filter(voucherType -> type.indexOf(voucherType.name) != NOT_FOUND_RESULT)
                .findFirst()
                .orElseThrow(() -> new NotFoundVoucherTypeException(ErrorCode.NOT_FOUND_VOUCHER_TYPE_EXCEPTION.getMessage()));
    }

    public static Voucher createVoucherFromFile(VoucherType voucherType, String stringId, String stringDiscount) {
        UUID voucherId = UUID.fromString(stringId.replace("voucherId=", ""));

        return switch (voucherType) {
            case FIXED_AMOUNT -> {
                long amount = Long.parseLong(stringDiscount.replace("amount=", "").trim());
                yield new FixedAmountVoucher(voucherId, amount);
            }
            case PERCENTAGE -> {
                long percent = Long.parseLong(stringDiscount.replace("percent=", "").trim());
                yield new PercentDiscountVoucher(voucherId, percent);
            }
            default -> throw new NotFoundVoucherTypeException(ErrorCode.NOT_FOUND_VOUCHER_TYPE_EXCEPTION.getMessage());
        };
    }
}
