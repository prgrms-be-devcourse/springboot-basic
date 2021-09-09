package org.prgrms.kdtspringhomework.command.create;

import org.prgrms.kdtspringhomework.voucher.service.VoucherService;

import java.util.Arrays;
import java.util.function.Supplier;

public enum CreateVoucherStatus {
    FIXED("fixed", CreateFixedVoucher::new),
    PERCENT("percent", CreatePercentVoucher::new);

    private final String command;
    private final Supplier<CreateVoucherStrategy> supplier;

    CreateVoucherStatus(final String command, final Supplier<CreateVoucherStrategy> supplier) {
        this.command = command;
        this.supplier = supplier;
    }

    public static boolean create(VoucherService voucherService, String voucherType, long amount) {
        CreateVoucherStatus userVoucherType = Arrays.stream(CreateVoucherStatus.values())
                .filter(createVoucherStatus -> createVoucherStatus.command.equals(voucherType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Select one of two things: 'fixed', 'percent'"));
        return userVoucherType.supplier.get().create(voucherService, amount);
    }
}
