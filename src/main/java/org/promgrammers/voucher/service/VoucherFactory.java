package org.promgrammers.voucher.service;

import org.promgrammers.voucher.domain.FixedAmountVoucher;
import org.promgrammers.voucher.domain.PercentDiscountVoucher;
import org.promgrammers.voucher.domain.Voucher;
import org.promgrammers.voucher.domain.VoucherType;
import org.promgrammers.voucher.domain.dto.VoucherRequestDto;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.UUID;


@Component
public class VoucherFactory {

    private final long MIN_FIXED_AMOUNT = 0L;
    private final long MIN_PERCENTAGE = 0L;
    private final long MAX_PERCENTAGE = 100L;

    public Voucher createVoucher(VoucherRequestDto voucherRequestDto) {


        VoucherType voucherType = voucherRequestDto.getVoucherType();
        long amount = voucherRequestDto.getAmount();

        switch (voucherType) {
            case FixedAmount:
                validateFixedAmount(amount);
                return new FixedAmountVoucher(amount, UUID.randomUUID());
            case PercentDiscount:
                validatePercentage(amount);
                return new PercentDiscountVoucher(amount, UUID.randomUUID());
            default:
                throw new NoSuchElementException("유효하지 않은 Voucher 입니다.");
        }
    }

    private void validatePercentage(long percentage) {
        if (!(MIN_PERCENTAGE <= percentage && percentage <= MAX_PERCENTAGE)) {
            throw new IllegalArgumentException("퍼센트 값은 0이상 100이하여야 합니다.");
        }
    }

    private void validateFixedAmount(long fixedAmount) {
        if (!(MIN_FIXED_AMOUNT <= fixedAmount)) {
            throw new IllegalArgumentException("금액은 0이상이어야 합니다.");
        }
    }
}
