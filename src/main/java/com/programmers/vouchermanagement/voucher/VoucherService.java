package com.programmers.vouchermanagement.voucher;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static com.programmers.vouchermanagement.constant.message.ExceptionMessage.INVALID_DISCOUNT_INPUT_MESSAGE;
import static com.programmers.vouchermanagement.constant.message.ExceptionMessage.VOUCHER_NOT_FOUND_MESSAGE;

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
            throw new NoSuchElementException(VOUCHER_NOT_FOUND_MESSAGE);
        }

        return vouchers;
    }

    public void validateDiscount(Voucher voucher) {
        if (!voucher.validatePositiveDiscount()) {
            throw new IllegalArgumentException(INVALID_DISCOUNT_INPUT_MESSAGE);
        }
    }
}
