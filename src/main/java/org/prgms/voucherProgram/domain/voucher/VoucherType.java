package org.prgms.voucherProgram.domain.voucher;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.BiFunction;

public enum VoucherType {
    FIXED_AMOUNT("1", FixedAmountVoucher::new),
    PERCENT_DISCOUNT("2", PercentDiscountVoucher::new);

    private static final String ERROR_WRONG_VOUCHER_COMMAND_MESSAGE = "[ERROR] 올바른 바우처 커맨드가 아닙니다.";

    private final String command;
    private final BiFunction<UUID, Long, Voucher> createVoucher;

    VoucherType(String command, BiFunction<UUID, Long, Voucher> createVoucher) {
        this.command = command;
        this.createVoucher = createVoucher;
    }

    public static VoucherType findByCommand(String command) {
        return Arrays.stream(VoucherType.values())
            .filter(type -> type.command.equals(command))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(ERROR_WRONG_VOUCHER_COMMAND_MESSAGE));
    }

    public Voucher createVoucher(UUID voucherId, long discountValue) {
        return createVoucher.apply(voucherId, discountValue);
    }
}
