package com.programmers.voucher.domain;

import com.programmers.voucher.dto.VoucherRequestDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class VoucherFactory {

    public Voucher createVoucher(VoucherRequestDto requestDto) {
        VoucherPolicy voucherPolicy = VoucherPolicy.of(requestDto.getVoucherType());

        return switch (voucherPolicy) {
            case FIXED_AMOUNT -> new FixedAmountVoucher(UUID.randomUUID(), requestDto.getDiscountAmount(), LocalDate.now());
            case PERCENT_DISCOUNT -> new PercentDiscountVoucher(UUID.randomUUID(), requestDto.getDiscountAmount(), LocalDate.now());
        };
    }
}
