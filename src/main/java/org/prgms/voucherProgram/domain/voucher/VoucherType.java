package org.prgms.voucherProgram.domain.voucher;

import java.util.Arrays;
import java.util.UUID;

import org.prgms.voucherProgram.utils.TriFunction;

public enum VoucherType {
    FIXED_AMOUNT("1", 1, FixedAmountVoucher::new),
    PERCENT_DISCOUNT("2", 2, PercentDiscountVoucher::new);

    private static final String ERROR_WRONG_VOUCHER_COMMAND_MESSAGE = "[ERROR] 올바른 바우처 커맨드가 아닙니다.";

    private final String command;
    private final int number;
    private final TriFunction<UUID, UUID, Long, Voucher> createVoucher;

    VoucherType(String command, int number, TriFunction<UUID, UUID, Long, Voucher> createVoucher) {
        this.command = command;
        this.number = number;
        this.createVoucher = createVoucher;
    }

    public static VoucherType findByCommand(String command) {
        return Arrays.stream(VoucherType.values())
            .filter(type -> type.command.equals(command))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(ERROR_WRONG_VOUCHER_COMMAND_MESSAGE));
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
}
