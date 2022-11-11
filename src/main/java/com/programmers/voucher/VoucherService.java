package com.programmers.voucher;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createFixedAmountVoucher(long discountAmount) {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), discountAmount);
        voucherRepository.insert(voucher);
    }

    public void createPercentDiscountVoucher(long discountPercent) {
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), discountPercent);
        voucherRepository.insert(voucher);
    }

    public Map<UUID, Voucher> findAll() {
        return voucherRepository.findAll();
    }
}
