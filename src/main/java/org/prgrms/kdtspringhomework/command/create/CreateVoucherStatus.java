package org.prgrms.kdtspringhomework.command.create;

import org.prgrms.kdtspringhomework.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.function.Supplier;

public enum CreateVoucherStatus {
    FIXED("fixed", CreateFixedVoucher::new),
    PERCENT("percent", CreatePercentVoucher::new);

    private static final Logger logger = LoggerFactory.getLogger(CreateVoucherStatus.class);

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
                .orElseThrow(() -> {
                    logger.error("Select one of two things: 'fixed', 'percent'");
                    return new IllegalArgumentException();
                });
        return userVoucherType.supplier.get().create(voucherService, amount);
    }
}
