package com.prgrms.voucher_manage.domain.voucher.controller;

import com.prgrms.voucher_manage.domain.voucher.dto.CreateVoucherDto;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.entity.VoucherType;
import com.prgrms.voucher_manage.domain.voucher.service.VoucherService;
import com.prgrms.voucher_manage.exception.InvalidInputException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

import static com.prgrms.voucher_manage.domain.voucher.entity.VoucherType.FIXED;
import static com.prgrms.voucher_manage.domain.voucher.entity.VoucherType.PERCENT;

@Controller
@RequiredArgsConstructor
public class VoucherController {
    private static final Long MIN_DISCOUNT_PRICE = 0L;
    private static final Long MIN_DISCOUNT_PERCENT = 0L;
    private static final Long MAX_DISCOUNT_PERCENT = 100L;
    private final VoucherService voucherService;

    public void createVoucher(VoucherType voucherType, Long discountAmount) {
        if (voucherType == FIXED && isInvalidPrice(discountAmount)) {
            throw new InvalidInputException("Invalid discount range.");
        } else if (voucherType == PERCENT && isInvalidPercent(discountAmount)) {
            throw new InvalidInputException("Invalid discount range.");
        }
        voucherService.createVoucher(new CreateVoucherDto(voucherType, discountAmount));
    }

    public List<Voucher> getVouchers() {
        return voucherService.getVouchers();
    }

    public boolean isInvalidPercent(Long discountAmount) {
        return (discountAmount < MIN_DISCOUNT_PERCENT) || (discountAmount > MAX_DISCOUNT_PERCENT);
    }

    public boolean isInvalidPrice(Long discountAmount) {
        return discountAmount < MIN_DISCOUNT_PRICE;
    }
}
