package com.blessing333.springbasic.voucher.converter;

import com.blessing333.springbasic.voucher.domain.Voucher;
import com.blessing333.springbasic.voucher.dto.ConvertedVoucherCreateForm;
import com.blessing333.springbasic.voucher.dto.VoucherCreateForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Converter {

    public ConvertedVoucherCreateForm convert(VoucherCreateForm form) throws ConvertFailException {
        Voucher.VoucherType type = Voucher.VoucherType.fromString(form.getVoucherType())
                .orElseThrow(()-> new ConvertFailException("잘못된 바우처 타입입니다"));
        int discountAmount = parseDiscountAmount(form.getDiscountAmount());
        if(discountAmount < 0)
            throw new ConvertFailException("할인 금액은 음수가 될 수 없습니다");
        return new ConvertedVoucherCreateForm(type,discountAmount);
    }

    private int parseDiscountAmount(String stringValue){
        try{
            return Integer.parseInt(stringValue);
        }catch (NumberFormatException e){
            throw new ConvertFailException("할인 금액은 숫자로 입력해주세요.");
        }
    }
}
