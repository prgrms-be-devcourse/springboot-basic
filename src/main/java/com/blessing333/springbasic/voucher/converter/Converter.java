package com.blessing333.springbasic.voucher.converter;

import com.blessing333.springbasic.voucher.VoucherType;
import com.blessing333.springbasic.voucher.dto.ConvertedVoucherCreateForm;
import com.blessing333.springbasic.voucher.dto.VoucherCreateForm;
import org.springframework.stereotype.Component;

@Component
public class Converter {
    public ConvertedVoucherCreateForm convert(VoucherCreateForm form){
        VoucherType type = VoucherType.fromString(form.getVoucherType()).get();
        int discountAmount = Integer.parseInt(form.getDiscountAmount());
        return new ConvertedVoucherCreateForm(type,discountAmount);
    }
}
