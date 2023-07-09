package com.prgrms.model.voucher.dto.discount;


import com.prgrms.model.voucher.VoucherType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DiscountCreatorTest {

    private DiscountCreator discountCreator;
    double value = 10;

    @BeforeEach
    public void setUp() {
        discountCreator = new DiscountCreator();
    }

    @Test
    @DisplayName(" 고정금액 할인 인자를 잘 만드는지 값과 타입을 확인한다.")
    public void givenFixVoucherType_WhenCreateDiscount_ThenEqualValue_And_Type() {
        // given
        VoucherType fixedVoucherType = VoucherType.FIXED_AMOUNT_VOUCHER;

        // when
        Discount fixCreatedDiscount = discountCreator.createDiscount(value, fixedVoucherType);

        // then
        assertThat(fixCreatedDiscount.getValue()).isEqualTo(10);
        assertThat(fixCreatedDiscount).isInstanceOf(FixedDiscount.class);
    }

    @Test
    @DisplayName(" 할인 인자를 잘 만드는지 값과 타입을 확인한다.")
    public void givenPercentVoucherType_WhenCreateDiscount_ThenEqualValue_And_Type() {
        // given
        VoucherType percentVoucherType = VoucherType.PERCENT_DISCOUNT_VOUCHER;

        // when
        Discount percentCreatedDiscount = discountCreator.createDiscount(value, percentVoucherType);

        // then
        assertThat(percentCreatedDiscount.getValue()).isEqualTo(10);
        assertThat(percentCreatedDiscount).isInstanceOf(PercentDiscount.class);
    }
}
