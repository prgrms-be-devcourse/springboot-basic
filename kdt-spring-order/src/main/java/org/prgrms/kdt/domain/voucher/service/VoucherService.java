package org.prgrms.kdt.domain.voucher.service;

import org.prgrms.kdt.domain.voucher.domain.Voucher;
import org.prgrms.kdt.domain.voucher.repository.VoucherRepository;

import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Can not find voucher for" + voucherId));
    }

    public void useVoucher(Voucher voucher) {
    }
}
