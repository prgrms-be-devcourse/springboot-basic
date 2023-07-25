package com.programmers.voucher.global.validation;

import com.programmers.voucher.domain.voucher.dto.request.VoucherSearchRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class VoucherSearchTimeValidator implements ConstraintValidator<VoucherSearchTimeValidation, VoucherSearchRequest> {
    @Override
    public void initialize(VoucherSearchTimeValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(VoucherSearchRequest request, ConstraintValidatorContext context) {
        LocalDateTime startTime = request.getStartTime();
        LocalDateTime endTime = request.getEndTime();
        if(validateSearchTime(startTime, endTime)) {
            return false;
        }
        return true;
    }

    private boolean validateSearchTime(LocalDateTime startTime, LocalDateTime endTime) {
        return startTime != null && endTime != null && startTime.isAfter(endTime);
    }
}
