package org.prgrms.kdt.voucher;

import org.prgrms.kdt.domain.FixedAmountVoucher;
import org.prgrms.kdt.domain.PercentDiscountVoucher;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.exception.ErrorCode;
import org.prgrms.kdt.exception.NotFindVoucherTypeException;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED_AMOUNT("1", "FixedAmountVoucher"),
    PERCENTAGE("2", "PercentDiscountVoucher");

    private final String typeValue;
    private final String expression;

    private static final String SEPARATOR = ". ";

    VoucherType(String typeValue, String expression) {
        this.typeValue = typeValue;
        this.expression = expression;
    }

    public static Voucher createVoucher(String type, long discountDegree) {
        VoucherType voucherType = VoucherType.selectVoucherType(type);
        return switch (voucherType) {
            case FIXED_AMOUNT -> new FixedAmountVoucher(UUID.randomUUID(), discountDegree);
            case PERCENTAGE -> new PercentDiscountVoucher(UUID.randomUUID(), discountDegree);
            default -> throw new NotFindVoucherTypeException(ErrorCode.NOT_FIND_VOUCHER_TYPE.getMessage());
        };
    }

    private static VoucherType selectVoucherType(String type) {
        return Stream.of(values())
                .filter(voucherType -> voucherType.typeValue.equals(type))
                .findFirst()
                .orElseThrow(() -> new NotFindVoucherTypeException(ErrorCode.NOT_FIND_VOUCHER_TYPE.getMessage()));
    }

    private static boolean isContainType(String type) {
        return Stream.of(values())
                .anyMatch(voucherType -> voucherType.typeValue.equals(type));
    }

    public static void checkType(String type) {
        if (!isContainType(type)) {
            throw new NotFindVoucherTypeException(ErrorCode.NOT_FIND_VOUCHER_TYPE.getMessage());
        }
    }


    public static String getAllVoucherExpression() {
        return Stream.of(values())
                .map(type -> type.typeValue + SEPARATOR + type.expression)
                .collect(Collectors.joining(System.lineSeparator()));
    }

}
