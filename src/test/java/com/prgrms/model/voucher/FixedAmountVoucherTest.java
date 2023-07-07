package com.prgrms.model.voucher;

import com.prgrms.model.order.OrderItem;
import com.prgrms.model.voucher.discount.Discount;
import com.prgrms.model.voucher.discount.FixedDiscount;
import com.prgrms.model.voucher.discount.PercentDiscount;
import com.prgrms.model.voucher.discount.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {
    private OrderItem orderItem;

    @BeforeEach
    public void setUp() {
        orderItem = new OrderItem(UUID.randomUUID(), 1000, 1);
    }
    @Test
    @DisplayName("할인된 금액이 제대로 나오는지 확인한다.")
    public void discountPrice_DiscountedPrice_Equal() {
        //given
        Voucher createdVoucher = new FixedAmountVoucher(new FixedDiscount(20), VoucherType.FIXED_AMOUNT_VOUCHER);
        //when
        Price discountedPrice = createdVoucher.discountPrice(orderItem);
        //then
        assertThat(discountedPrice.getValue()).isEqualTo(980);
    }

    @Test
    @DisplayName("할인금액이 원가보다 커 할인된 금액이 음수가 나오는 경우 예외를 던지는지 확인한다.")
    public void discountPrice_NegativeDiscountedPrice_ThrowsException() {
        //given
        Voucher createdVoucher = new FixedAmountVoucher(new FixedDiscount(2000), VoucherType.FIXED_AMOUNT_VOUCHER);
        //when_then
        assertThatThrownBy(() ->createdVoucher.discountPrice(orderItem))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
