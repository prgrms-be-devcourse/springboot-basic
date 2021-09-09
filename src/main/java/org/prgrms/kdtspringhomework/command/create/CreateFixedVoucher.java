package org.prgrms.kdtspringhomework.command.create;

import org.prgrms.kdtspringhomework.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdtspringhomework.voucher.service.VoucherService;

import java.util.UUID;

public class CreateFixedVoucher implements CreateVoucherStrategy {
    @Override
    public boolean create(VoucherService voucherService, long amount) {
        voucherService.addVoucher(new FixedAmountVoucher(UUID.randomUUID(), amount));
        return true;
    }
}