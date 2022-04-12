package org.voucherProject.voucherProject.entity.voucher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.internal.Function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public enum VoucherType {
    FIXED(1, amount -> new FixedAmountVoucher(UUID.randomUUID(), amount)),
    PERCENT(2, amount -> new PercentDiscountVoucher(UUID.randomUUID(), amount));

    private final int number;
    private Function<Long, Voucher> createVoucherExpression;

    VoucherType(int number, Function<Long, Voucher> createVoucherExpression) {
        this.number = number;
        this.createVoucherExpression = createVoucherExpression;
    }

    public int getNumber() {
        return number;
    }

    public Voucher createVoucher(long amount) {
        return createVoucherExpression.apply(amount);
    }

    public static Voucher createVoucher(int inputVoucherTypeInt, long amount) {
        Optional<VoucherType> voucherType = Arrays.stream(VoucherType.values())
                .filter(v -> v.getNumber() == inputVoucherTypeInt)
                .findFirst();

        if (voucherType.isEmpty()) throw new IllegalArgumentException();
        return voucherType.get().createVoucher(amount);
    }

    public static void validateVoucherType(int input) {
        if (!Arrays.stream(VoucherType.values())
                .mapToInt(v -> v.getNumber())
                .anyMatch(v -> v == input)) {
            throw new IllegalArgumentException();
        }
    }


}
