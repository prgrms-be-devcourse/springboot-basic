package com.devcourse.springbootbasic.application.voucher.model;

import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchException;

class PriceTest {

    @Test
    @DisplayName("정상적인 결과가 나오는 값과 바우처 적용 시 결과 반환된다.")
    void applyDiscount_ParamValidValues_ReturnPrice() {
        DiscountValue fixedDiscountValue = new DiscountValue(VoucherType.FIXED_AMOUNT, 100);
        DiscountValue percentDiscountValue = new DiscountValue(VoucherType.PERCENT_DISCOUNT, 23);
        Price originPrice = new Price(1000);

        Price fixedResult = originPrice.applyDiscount(fixedDiscountValue);
        Price percentResult = originPrice.applyDiscount(percentDiscountValue);

        assertThat(fixedResult).isInstanceOf(Price.class);
        assertThat(percentResult).isInstanceOf(Price.class);
    }

    @Test
    @DisplayName("비정상적인 결과가 나오는 값과 바우처 적용 시 실패한다.")
    void applyDiscount_ParamInvalidValues_Exception() {
        DiscountValue fixedDiscountValue = new DiscountValue(VoucherType.FIXED_AMOUNT, 100);
        Price originPrice = new Price(2);

        Exception exception = catchException(() -> originPrice.applyDiscount(fixedDiscountValue));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

}