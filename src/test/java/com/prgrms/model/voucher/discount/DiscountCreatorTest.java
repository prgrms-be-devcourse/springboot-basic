package com.prgrms.model.voucher.discount;


import static org.assertj.core.api.Assertions.assertThat;

import com.prgrms.model.voucher.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class DiscountCreatorTest {

    @Autowired
    private DiscountCreator discountCreator;

    private final double VALUE = 10;

    @Test
    @DisplayName("고정금액을 나타내는 값 객체를 잘 만드는지 값과 타입을 확인한다.")
    void givenFixVoucherType_WhenCreateDiscount_ThenEqualValue_And_Type() {
        // given
        VoucherType fixedVoucherType = VoucherType.FIXED_AMOUNT_VOUCHER;

        // when
        Discount fixCreatedDiscount = discountCreator.createDiscount(fixedVoucherType, VALUE);

        // then
        assertThat(fixCreatedDiscount.getDiscountAmount()).isEqualTo(10);
        assertThat(fixCreatedDiscount).isInstanceOf(FixedDiscount.class);
    }

    @Test
    @DisplayName("할인율을 나타내는 값 객체를 잘 만드는지 값과 타입을 확인한다.")
    void givenPercentVoucherType_WhenCreateDiscount_ThenEqualValue_And_Type() {
        // given
        VoucherType percentVoucherType = VoucherType.PERCENT_DISCOUNT_VOUCHER;

        // when
        Discount percentCreatedDiscount = discountCreator.createDiscount(percentVoucherType, VALUE);

        // then
        assertThat(percentCreatedDiscount.getDiscountAmount()).isEqualTo(10);
        assertThat(percentCreatedDiscount).isInstanceOf(PercentDiscount.class);
    }

}
