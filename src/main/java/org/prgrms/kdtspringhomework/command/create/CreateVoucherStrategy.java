package org.prgrms.kdtspringhomework.command.create;

import org.prgrms.kdtspringhomework.voucher.service.VoucherService;

@FunctionalInterface
public interface CreateVoucherStrategy {
    boolean create(VoucherService voucherService, long amount);
}
