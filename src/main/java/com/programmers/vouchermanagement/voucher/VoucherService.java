package com.programmers.vouchermanagement.voucher;

import java.util.List;
import java.util.NoSuchElementException;

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

    public List<Voucher> readAllVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();

        if (vouchers.isEmpty()) {
            throw new NoSuchElementException("There is no voucher registered.");
        }

        return vouchers;
    }

    public void validateDiscount(Voucher voucher) {
        if (!voucher.validatePositiveDiscount()) {
            throw new IllegalArgumentException("Input should be a number greater than 0");
        }
    }
}
