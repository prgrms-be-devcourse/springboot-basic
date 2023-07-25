package com.programmers.voucher.global.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = VoucherSearchTimeValidator.class)
@Documented
public @interface VoucherSearchTimeValidation {
    String message() default "The search start time must be before the end time";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
