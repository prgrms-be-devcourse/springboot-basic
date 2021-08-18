package org.prgrms.kdt.engine.voucher;

import org.prgrms.kdt.engine.voucher.Voucher;
import org.prgrms.kdt.engine.voucher.VoucherRepository;

import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Cannot find the voucher for " + voucherId));
    }

    public void userVoucher(Voucher voucher) {
    }

    public Voucher createFixedVoucher(long amount) {
        var voucher = new FixedAmountVoucher(UUID.randomUUID(), amount);
        voucherRepository.insertVoucher(voucher);
        return voucher;
    }

    public Voucher createPercentVoucher(long percent) {
        var voucher = new PercentDiscountVoucher(UUID.randomUUID(), percent);
        voucherRepository.insertVoucher(voucher);
        return voucher;
    }
}
