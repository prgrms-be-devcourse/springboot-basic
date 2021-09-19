package org.prgrms.kdtspringhomework.command;

import org.prgrms.kdtspringhomework.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdtspringhomework.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdtspringhomework.voucher.domain.Voucher;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.BiFunction;

public enum CreateVoucherStatus {
    FIXED("fixed", FixedAmountVoucher::new),
    PERCENT("percent", PercentDiscountVoucher::new);

    private final String command;
    private final BiFunction<UUID, Long, Voucher> biFunction;

    CreateVoucherStatus(String command, BiFunction<UUID, Long, Voucher> biFunction) {
        this.command = command;
        this.biFunction = biFunction;
    }

    public static Voucher getVoucher(String voucherType, UUID voucherId, long amount) {
        CreateVoucherStatus userVoucherType = Arrays.stream(CreateVoucherStatus.values())
                .filter(createVoucherStatus -> createVoucherStatus.command.equals(voucherType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Select one of two things: 'fixed', 'percent'"));
        return userVoucherType.createVoucher(voucherId, amount);
    }

    public Voucher createVoucher(UUID voucherId, Long amount) {
        return biFunction.apply(voucherId, amount);
    }
}
