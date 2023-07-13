package com.prgrms.model.voucher;

import com.prgrms.model.order.OrderItem;
import com.prgrms.model.order.Price;
import com.prgrms.model.voucher.discount.FixedDiscount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FixedAmountVoucherTest {

    private final int voucherId = 1;
    private final int orderId = 1;
    private final int quantity = 1;
    private OrderItem orderItem;
    private Price productPrice = new Price(1000);

    @BeforeEach
    void setUp() {
        orderItem = new OrderItem(orderId, productPrice, quantity);
    }

    @Test
    @DisplayName("고정 할인 정책에 대해 알맞은 할인된 금액으로 나온다.")
    void discountPrice_DiscountedPrice_Equal() {
        //given
        Voucher createdVoucher = new FixedAmountVoucher(voucherId, new FixedDiscount(20), VoucherType.FIXED_AMOUNT_VOUCHER);

        //when
        Price discountedPrice = createdVoucher.discountPrice(orderItem);

        //then
        assertThat(discountedPrice.cost()).isEqualTo(980);
    }

    @Test
    @DisplayName("할인금액이 원가보다 커 할인된 금액이 음수가 나오는 경우 예외를 던진다.")
    void discountPrice_NegativeDiscountedPrice_ThrowsException() {
        //given
        Voucher createdVoucher = new FixedAmountVoucher(voucherId, new FixedDiscount(2000), VoucherType.FIXED_AMOUNT_VOUCHER);

        //when_then
        assertThatThrownBy(() -> createdVoucher.discountPrice(orderItem))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
