package com.prgrms.model.voucher.discount;


import com.prgrms.model.voucher.VoucherType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class DiscountCreatorTest {

    private DiscountCreator discountCreator;
    double value = 10;

    @BeforeEach
    public void setUp(){
        discountCreator = new DiscountCreator();
    }

    @Test
    @DisplayName(" 고정금액 할인 인자를 잘 만드는지 값과 타입을 확인한다.")
    public void GivenFixVoucherType_WhenCreateDiscount_ThenEqualValue_And_Type() {
        // Given
        VoucherType fixedVoucherType = VoucherType.FIXED_AMOUNT_VOUCHER;
        // When
        Discount fixCreatedDiscount = discountCreator.createDiscount(value,fixedVoucherType);
        // Then
        assertThat(fixCreatedDiscount.getValue()).isEqualTo(10);
        assertThat(fixCreatedDiscount).isInstanceOf(FixedDiscount.class);
    }

    @Test
    @DisplayName(" 할인 인자를 잘 만드는지 값과 타입을 확인한다.")
    public void GivenPercentVoucherType_WhenCreateDiscount_ThenEqualValue_And_Type() {
        // Given
        VoucherType percentVoucherType = VoucherType.PERCENT_DISCOUNT_VOUCHER;
        // When
        Discount percentCreatedDiscount = discountCreator.createDiscount(value,percentVoucherType);
        // Then
        assertThat(percentCreatedDiscount.getValue()).isEqualTo(10);
        assertThat(percentCreatedDiscount).isInstanceOf(PercentDiscount.class);
    }
}
