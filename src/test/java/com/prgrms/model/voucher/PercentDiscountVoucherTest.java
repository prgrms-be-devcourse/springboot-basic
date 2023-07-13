package com.prgrms.model.voucher;

import com.prgrms.model.order.OrderItem;
import com.prgrms.model.order.Price;
import com.prgrms.model.voucher.discount.PercentDiscount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PercentDiscountVoucherTest {

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
    @DisplayName("할인율 정책이 적용되어 알맞은 할인된 금액으로 나온다.")
    void discountPrice_DiscountedPrice_Equal() {
        //given
        Voucher createdVoucher = new PercentDiscountVoucher(voucherId, new PercentDiscount(20), VoucherType.PERCENT_DISCOUNT_VOUCHER);

        //when
        Price discountedPrice = createdVoucher.discountPrice(orderItem);

        //then
        assertThat(discountedPrice.cost()).isEqualTo(800);
    }
}
