package org.prgms.voucheradmin.domain.voucher.service;

import org.prgms.voucheradmin.domain.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createVoucher() {}

    public void getVouchers() {}
}
