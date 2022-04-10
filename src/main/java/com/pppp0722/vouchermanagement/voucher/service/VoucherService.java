package com.pppp0722.vouchermanagement.voucher.service;

import com.pppp0722.vouchermanagement.voucher.model.FixedAmountVoucher;
import com.pppp0722.vouchermanagement.voucher.model.PercentDiscountVoucher;
import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import com.pppp0722.vouchermanagement.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createVoucher(UUID voucherId, String type, long amount) {
        Voucher voucher = null;

        if(type.equals("f")) {
            voucher = new FixedAmountVoucher(UUID.randomUUID(), amount);
        } else if(type.equals("p")) {
            voucher = new PercentDiscountVoucher(UUID.randomUUID(), amount);
        }

        voucherRepository.insert(voucher);
    }
}
