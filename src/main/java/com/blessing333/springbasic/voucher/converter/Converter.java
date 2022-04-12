package com.blessing333.springbasic.voucher.converter;

import com.blessing333.springbasic.voucher.VoucherType;
import com.blessing333.springbasic.voucher.dto.ConvertedVoucherCreateForm;
import com.blessing333.springbasic.voucher.dto.VoucherCreateForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Converter {
    private final VoucherFormValidator validator;

    public ConvertedVoucherCreateForm convert(VoucherCreateForm form) throws ConvertFailException {
        validator.validateVoucherCreateForm(form);
        VoucherType type = VoucherType.fromString(form.getVoucherType()).get();
        int discountAmount = Integer.parseInt(form.getDiscountAmount());
        return new ConvertedVoucherCreateForm(type,discountAmount);
    }
}
