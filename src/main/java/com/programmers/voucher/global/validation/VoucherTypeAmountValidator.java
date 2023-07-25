package com.programmers.voucher.global.validation;

import com.programmers.voucher.domain.voucher.domain.VoucherType;
import com.programmers.voucher.domain.voucher.dto.request.VoucherCreateRequest;
import com.programmers.voucher.domain.voucher.util.VoucherDiscountRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.programmers.voucher.domain.voucher.util.VoucherDiscountRange.FIXED_AMOUNT_MIN;
import static com.programmers.voucher.domain.voucher.util.VoucherDiscountRange.PERCENT_DISCOUNT_MIN;

public class VoucherTypeAmountValidator
        implements ConstraintValidator<VoucherTypeAmountValidation, VoucherCreateRequest> {
    @Override
    public void initialize(VoucherTypeAmountValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(VoucherCreateRequest request, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        VoucherType voucherType = request.getVoucherType();
        long amount = request.getAmount();
        if(voucherType.equals(VoucherType.FIXED_AMOUNT) && validateFixedAmount(amount)) {
            context.buildConstraintViolationWithTemplate("Fixed amount must be positive")
                    .addConstraintViolation();
            return false;
        }
        if (voucherType.equals(VoucherType.PERCENT) && validatePercent(amount)) {
            context.buildConstraintViolationWithTemplate("Percent discount must be between 0 and 100")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean validatePercent(long amount) {
        return amount <= PERCENT_DISCOUNT_MIN || amount >= VoucherDiscountRange.PERCENT_DISCOUNT_MAX;
    }

    private boolean validateFixedAmount(long amount) {
        return amount <= FIXED_AMOUNT_MIN;
    }
}
