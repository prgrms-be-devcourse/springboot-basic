package com.example.voucher.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.voucher.domain.FixedAmountVoucher;
import com.example.voucher.domain.PercentDiscountVoucher;
import com.example.voucher.domain.Voucher;
import com.example.voucher.constant.VoucherType;
import com.example.voucher.repository.VoucherRepository;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherType voucherType, long discount) {
        Voucher voucher = switch (voucherType) {
            case FIXED_AMOUNT_DISCOUNT -> new FixedAmountVoucher(discount);
            case PERCNET_DISCOUNT -> new PercentDiscountVoucher(discount);
        };

        voucherRepository.save(voucher);

        return voucher;
    }

    public List<Voucher> getVouchers() {
        return voucherRepository.findAll();
    }

}
