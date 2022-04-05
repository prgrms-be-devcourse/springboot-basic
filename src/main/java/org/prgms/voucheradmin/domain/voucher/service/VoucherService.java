package org.prgms.voucheradmin.domain.voucher.service;

import org.prgms.voucheradmin.domain.voucher.dao.VoucherRepository;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }
}
