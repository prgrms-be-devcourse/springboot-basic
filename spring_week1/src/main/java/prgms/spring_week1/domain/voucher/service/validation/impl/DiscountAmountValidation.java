package prgms.spring_week1.domain.voucher.service.validation.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import prgms.spring_week1.domain.voucher.service.validation.DiscountValueValidation;

@Component
@Qualifier("amount")
public class DiscountAmountValidation implements DiscountValueValidation {
    @Override
    public boolean invalidateInsertDiscountValue(long discount) {
        return discount <= 0;
    }
}
