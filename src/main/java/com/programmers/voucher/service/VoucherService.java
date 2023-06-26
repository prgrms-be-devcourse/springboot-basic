package com.programmers.voucher.service;

import com.programmers.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.repository.VoucherRepository;
import com.programmers.voucher.view.dto.DiscountAmount;
import com.programmers.voucher.view.dto.VoucherCommand;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherCommand voucherCommand, DiscountAmount discountAmount) {
        Voucher voucher = switch (voucherCommand) {
            case FIXED_AMOUNT -> new FixedAmountVoucher(UUID.randomUUID(), discountAmount.getAmount());
            case PERCENT_DISCOUNT -> new PercentDiscountVoucher(UUID.randomUUID(), discountAmount.getAmount());
        };
        return voucherRepository.insert(voucher);
    }
}
