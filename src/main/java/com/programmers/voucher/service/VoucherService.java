package com.programmers.voucher.service;

import com.programmers.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.repository.VoucherRepository;
import com.programmers.voucher.view.dto.DiscountAmount;
import com.programmers.voucher.view.dto.VoucherType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherType voucherType, DiscountAmount discountAmount) {
        Voucher voucher = switch (voucherType) {
            case FIXED_AMOUNT -> new FixedAmountVoucher(UUID.randomUUID(), discountAmount.getAmount());
            case PERCENT_DISCOUNT -> new PercentDiscountVoucher(UUID.randomUUID(), discountAmount.getAmount());
        };
        return voucherRepository.insert(voucher);
    }

    public List<Voucher> getVoucherList() {
        return voucherRepository.findAll();
    }
}
