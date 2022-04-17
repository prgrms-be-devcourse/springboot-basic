package org.prgms.voucherProgram.domain.voucher;

import java.util.Arrays;
import java.util.UUID;

import org.prgms.voucherProgram.utils.TriFunction;

public enum VoucherType {
    FIXED_AMOUNT(1, FixedAmountVoucher::new),
    PERCENT_DISCOUNT(2, PercentDiscountVoucher::new);

    private static final String ERROR_WRONG_VOUCHER_COMMAND_MESSAGE = "[ERROR] 올바른 바우처 타입이 아닙니다.";

    private final int number;
    private final TriFunction<UUID, UUID, Long, Voucher> createVoucher;

    VoucherType(int number, TriFunction<UUID, UUID, Long, Voucher> createVoucher) {
        this.number = number;
        this.createVoucher = createVoucher;
    }

    public static VoucherType findByNumber(int number) {
        return Arrays.stream(VoucherType.values())
            .filter(type -> type.number == number)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(ERROR_WRONG_VOUCHER_COMMAND_MESSAGE));
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(VoucherType.values()));
    }

    public Voucher createVoucher(UUID voucherId, UUID customerId, long discountValue) {
        return createVoucher.apply(voucherId, customerId, discountValue);
    }

    public int getNumber() {
        return number;
    }
}
