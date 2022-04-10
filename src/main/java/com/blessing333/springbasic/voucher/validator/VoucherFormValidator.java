package com.blessing333.springbasic.voucher.validator;

import com.blessing333.springbasic.voucher.VoucherCreateFailException;
import com.blessing333.springbasic.voucher.VoucherType;
import com.blessing333.springbasic.voucher.dto.VoucherCreateForm;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VoucherFormValidator {

    public void validVoucherCreateForm(VoucherCreateForm form) throws VoucherCreateFailException{
        validAmountIsDigit(form.getDiscountAmount());
        Optional<VoucherType> voucherType = VoucherType.fromString(form.getVoucherType());
        VoucherType type = voucherType.orElseThrow(() -> new VoucherCreateFailException("존재하지 않는 바우쳐 타입입니다."));
        if(type == VoucherType.PERCENT){
            validPercentageRange(form.getDiscountAmount());
        }
    }

    private void validAmountIsDigit(String amount) throws VoucherCreateFailException{
        for (int i = 0; i < amount.length(); i++) {
            char c = amount.charAt(i);
            if(!Character.isDigit(c))
                throw new VoucherCreateFailException("할인 금액은 숫자만 올 수 있습니다.");
        }
    }

    //입력 받는 문자열 매개변수가 숫자로 이루어져 있는지 먼저 검증 필요
    private void validPercentageRange(String percent) throws VoucherCreateFailException{
        int intPercent = Integer.parseInt(percent);
        if(intPercent < 0 || intPercent > 100)
            throw new VoucherCreateFailException("할인 비율은 0초과 100 이하로 입력해야합니다");
    }
}
