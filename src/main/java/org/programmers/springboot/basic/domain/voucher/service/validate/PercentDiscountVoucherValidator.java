package org.programmers.springboot.basic.domain.voucher.service.validate;

import org.programmers.springboot.basic.AppConstants;
import org.programmers.springboot.basic.domain.voucher.exception.IllegalDiscountException;
import org.springframework.stereotype.Component;

@Component
public class PercentDiscountVoucherValidator implements ValidateHandler {

    @Override
    public void validate(Long discount) {
        if (discount < AppConstants.MIN_PERCENT_DISCOUNT || discount > AppConstants.MAX_PERCENT_DISCOUNT) {
            throw new IllegalDiscountException("Exception Occurred: Illegal Discount value! Possible from 0 to 100.");
        }
    }
}
