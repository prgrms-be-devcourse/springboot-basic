package org.prgrms.kdt.validate;

import org.prgrms.kdt.form.VoucherForm;
import org.prgrms.kdt.voucher.VoucherType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by yhh1056
 * Date: 2021/09/09 Time: 4:49 오후
 */
@Component
public class VoucherFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(VoucherForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        VoucherForm voucherForm = (VoucherForm) target;

        VoucherType voucherType = VoucherType.valueOf(voucherForm.getVoucherType());
        Long discount = Long.valueOf(voucherForm.getDiscount());

        if (voucherType == VoucherType.PERCENT && discount > 100L) {
            errors.rejectValue("discount",
                    "invalid.discount",
                    new Object[]{voucherForm.getVoucherType()},
                    "100퍼센트를 넘길 수 없습니다.");
        }

    }
}
