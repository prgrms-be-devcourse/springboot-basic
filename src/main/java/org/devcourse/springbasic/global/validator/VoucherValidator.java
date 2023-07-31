package org.devcourse.springbasic.global.validator;

import org.devcourse.springbasic.domain.voucher.domain.Voucher;

public class VoucherValidator implements Validator<Voucher> {

    @Override
    public boolean validate(Voucher voucher) {

        long maxDiscountRate = voucher.maxDiscountRate();

        if(voucher.getDiscountRate() <= 0) throw new IllegalArgumentException("할인금액을 올바르게 입력하세요. 0원 이상이어야 합니다.");
        else if(voucher.getDiscountRate() > maxDiscountRate) {
            throw new IllegalArgumentException(String.format("할인금액 %d보다 작아야 합니다", maxDiscountRate));
        }
        return true;
    }
}