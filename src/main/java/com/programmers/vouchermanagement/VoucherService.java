package com.programmers.vouchermanagement;

import org.springframework.stereotype.Service;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher create(Voucher voucher) {
        validateDiscount(voucher);
        voucherRepository.save(voucher);
        return voucher;
    }

    public void validateDiscount(Voucher voucher) {
        if (!voucher.validatePositiveDiscount()) {
            throw new IllegalArgumentException("input should be a number greater than 0");
        }
    }
}
