package com.blessing333.springbasic.voucher.converter;

import com.blessing333.springbasic.voucher.VoucherType;
import com.blessing333.springbasic.voucher.dto.VoucherCreateForm;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VoucherFormValidator {
    public void validateVoucherCreateForm(VoucherCreateForm form){
        validateAmountIsPositiveNumber(form.getDiscountAmount());
        Optional<VoucherType> voucherType = VoucherType.fromString(form.getVoucherType());
        VoucherType type = voucherType.orElseThrow(() -> new ConvertFailException("존재하지 않는 바우쳐 타입입니다."));
        if (type == VoucherType.PERCENT) {
            validPercentageRange(form.getDiscountAmount());
        }
    }

    private void validateAmountIsPositiveNumber(String amount){
        for (int i = 0; i < amount.length(); i++) {
            char c = amount.charAt(i);
            if (!Character.isDigit(c)) throw new ConvertFailException("할인 금액은 숫자만 올 수 있습니다.");
        }
    }

    //입력 받는 문자열 매개변수가 숫자로 이루어져 있는지 먼저 검증 필요
    private void validPercentageRange(String percent) throws ConvertFailException {
        int intPercent = Integer.parseInt(percent);
        if (intPercent < 0 || intPercent > 100) throw new ConvertFailException("할인 비율은 0초과 100 이하로 입력해야합니다");
    }
}
