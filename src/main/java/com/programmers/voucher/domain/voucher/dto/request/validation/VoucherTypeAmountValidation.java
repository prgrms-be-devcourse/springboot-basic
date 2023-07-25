package com.programmers.voucher.domain.voucher.dto.request.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = VoucherTypeAmountValidator.class)
@Documented
public @interface VoucherTypeAmountValidation {
    String message() default "Voucher amount is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
