package com.example.demo.service;

import com.example.demo.domain.voucher.FixedAmountVoucher;
import com.example.demo.domain.voucher.PercentDiscountVoucher;
import com.example.demo.domain.voucher.Voucher;
import com.example.demo.domain.voucher.VoucherRepository;
import com.example.demo.util.VoucherType;
import java.util.UUID;

public class VoucherService {

    VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createVoucher(VoucherType voucherType, int amount) {
        Voucher voucher = switch (voucherType) {
            case FIXED_AMOUNT_VOUCHER -> new FixedAmountVoucher(UUID.randomUUID(), amount);
            case PERCENT_DISCOUNT_VOUCHER -> new PercentDiscountVoucher(UUID.randomUUID(), amount);
            default -> throw new IllegalArgumentException("잘 못된 바우처 타입입니다."); //Enum쓰는 Switch문에 default가 필요할까?
        };

        saveVoucher(voucher);
    }

    private void saveVoucher(Voucher voucher) {
        voucherRepository.save(voucher);
    }
}
