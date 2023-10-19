package com.programmers.vouchermanagement.voucher;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class VoucherService {
    //messages
    private static final String VOUCHER_NOT_FOUND_MESSAGE =
            "There is no voucher registered.";

    //TODO: move this message to factory method
    private static final String INVALID_DISCOUNT_INPUT_MESSAGE =
            "Input should be a number greater than 0";
    //---

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher create(CreateVoucherRequestDTO createVoucherRequestDTO) {
        //TODO: static factory method + validation of discountValue
        Voucher voucher = switch (createVoucherRequestDTO.createVoucherMenu()) {
            case FIXED -> new FixedAmountVoucher(UUID.randomUUID(), createVoucherRequestDTO.discountAmount());
            case PERCENT -> new PercentVoucher(UUID.randomUUID(), createVoucherRequestDTO.discountAmount());
        };
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
}
