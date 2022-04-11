package com.example.voucher_manager.domain.service;

import com.example.voucher_manager.domain.repository.VoucherRepository;
import com.example.voucher_manager.domain.voucher.FixedAmountVoucher;
import com.example.voucher_manager.domain.voucher.PercentDiscountVoucher;
import com.example.voucher_manager.domain.voucher.Voucher;
import com.example.voucher_manager.domain.voucher.VoucherType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public Voucher createVoucher(VoucherType voucherType, Long discountInformation) {
        Voucher voucher = switch (voucherType) {
            case FIXED -> new FixedAmountVoucher(UUID.randomUUID(), discountInformation);
            case PERCENT -> new PercentDiscountVoucher(UUID.randomUUID(), discountInformation);
            default -> throw new IllegalStateException("Unexpected value: " + voucherType);
        };

        voucherRepository.insert(voucher);
        return voucher;
    }
}
