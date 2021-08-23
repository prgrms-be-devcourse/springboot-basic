package org.prgrms.kdtspringhomework.voucher.service;

import org.prgrms.kdtspringhomework.voucher.domain.Voucher;
import org.prgrms.kdtspringhomework.voucher.repository.VoucherRepository;

import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(String.format("Can not find a voucher for %s", voucherId)));
    }

    public void useVoucher(Voucher voucher) {

    }
}
