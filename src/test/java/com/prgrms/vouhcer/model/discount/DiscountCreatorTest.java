package com.prgrms.vouhcer.model.discount;

import static org.assertj.core.api.Assertions.assertThat;

import com.prgrms.voucher.model.VoucherType;
import com.prgrms.voucher.model.discount.Discount;
import com.prgrms.voucher.model.discount.DiscountCreator;
import com.prgrms.voucher.model.discount.FixedDiscount;
import com.prgrms.voucher.model.discount.PercentDiscount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class DiscountCreatorTest {

    @Autowired
    DiscountCreator discountCreator;

    final double VALUE = 10;

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
