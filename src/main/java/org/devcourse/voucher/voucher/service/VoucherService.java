package org.devcourse.voucher.voucher.service;

import org.devcourse.voucher.customer.repository.BlacklistRepository;
import org.devcourse.voucher.voucher.model.Voucher;
import org.devcourse.voucher.voucher.model.VoucherType;
import org.devcourse.voucher.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;


@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final BlacklistRepository blacklistRepository;

    public VoucherService(VoucherRepository voucherRepository, BlacklistRepository blacklistRepository) {
        this.voucherRepository = voucherRepository;
        this.blacklistRepository = blacklistRepository;
    }

    public Voucher createVoucher(VoucherType voucherType, long discount) {
        return null;
    }
}
