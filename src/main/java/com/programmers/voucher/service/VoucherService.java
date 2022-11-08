package com.programmers.voucher.service;

import com.programmers.voucher.model.FixedAmountVoucher;
import com.programmers.voucher.model.PercentDiscountVoucher;
import com.programmers.voucher.model.Voucher;
import com.programmers.voucher.model.VoucherType;
import com.programmers.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher create(VoucherType voucherType, long discountValue) {
        Voucher newVoucher;
        if (voucherType == VoucherType.FIXED_AMOUNT_VOUCHER) {
            newVoucher = new FixedAmountVoucher(UUID.randomUUID(), discountValue);
        } else {
            newVoucher = new PercentDiscountVoucher(UUID.randomUUID(), discountValue);
        }
        return voucherRepository.save(newVoucher);
    }

    public List<Voucher> findAllVoucher() {
        return voucherRepository.findAll();
    }
}
