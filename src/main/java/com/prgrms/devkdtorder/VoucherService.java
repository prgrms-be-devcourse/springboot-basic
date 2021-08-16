package com.prgrms.devkdtorder;

import java.util.UUID;

public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("cCan not find a voucher for " + voucherId));
    }

    public void useVoucher(Voucher voucher) {

    }
}
