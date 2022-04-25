package com.blessing333.springbasic.voucher.converter;

import com.blessing333.springbasic.voucher.domain.Voucher;
import com.blessing333.springbasic.voucher.dto.VoucherCreateForm;
import com.blessing333.springbasic.voucher.dto.VoucherCreateFormPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoucherConverter {

    public VoucherCreateForm convert(VoucherCreateFormPayload form){
        Voucher.VoucherType type = toVoucherType(form.getVoucherType());
        int discountAmount = parseDiscountAmount(form.getDiscountAmount());
        if(discountAmount < 0)
            throw new ConvertFailException("할인 금액은 음수가 될 수 없습니다");
        return new VoucherCreateForm(type,discountAmount);
    }

    public Voucher.VoucherType toVoucherType(String string){
        return Voucher.VoucherType.fromString(string).orElseThrow(()->new ConvertFailException("지원하지 않는 바우처 타입입니다"));
    }

    private int parseDiscountAmount(String stringValue){
        try{
            return Integer.parseInt(stringValue);
        }catch (NumberFormatException e){
            throw new ConvertFailException("할인 금액은 숫자로 입력해주세요.");
        }
    }
}
