package com.programmers.vouchermanagement.voucher;

import com.programmers.vouchermanagement.consolecomponent.CreateVoucherMenu;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class VoucherService {
    //messages
    private static final String VOUCHER_NOT_FOUND_MESSAGE =
            "There is no voucher registered.";
    //---

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher create(CreateVoucherRequestDTO createVoucherRequestDTO) {
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
