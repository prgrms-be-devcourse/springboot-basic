package com.example.voucher_manager.domain.voucher;

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
            case FIXED -> new FixedAmountVoucher(UUID.randomUUID(), discountInformation, voucherType);
            case PERCENT -> new PercentDiscountVoucher(UUID.randomUUID(), discountInformation, voucherType);
            default -> throw new IllegalStateException("Unexpected value: " + voucherType);
        };

        voucherRepository.insert(voucher);
        return voucher;
    }
}
