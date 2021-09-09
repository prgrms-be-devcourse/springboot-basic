package org.prgrms.kdtspringhomework.command.create;

import org.prgrms.kdtspringhomework.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdtspringhomework.voucher.service.VoucherService;

import java.util.UUID;

public class CreatePercentVoucher implements CreateVoucherStrategy {
    @Override
    public boolean create(VoucherService voucherService, long amount) {
        voucherService.addVoucher(new PercentDiscountVoucher(UUID.randomUUID(), amount));
        return true;
    }
}