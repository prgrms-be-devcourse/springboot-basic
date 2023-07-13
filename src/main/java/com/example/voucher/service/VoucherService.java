package com.example.voucher.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.voucher.constant.VoucherType;
import com.example.voucher.domain.FixedAmountVoucher;
import com.example.voucher.domain.PercentDiscountVoucher;
import com.example.voucher.domain.Voucher;
import com.example.voucher.repository.VoucherRepository;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherType voucherType, long discountValue) {
        Voucher createdVoucher = switch (voucherType) {
            case FIXED_AMOUNT_DISCOUNT -> new FixedAmountVoucher(discountValue);
            case PERCENT_DISCOUNT -> new PercentDiscountVoucher(discountValue);
        };

        return voucherRepository.save(createdVoucher);
    }

    public List<Voucher> getVouchers() {
        return voucherRepository.findAll();
    }

    public void deleteVouchers() {
        voucherRepository.deleteAll();
    }

}
