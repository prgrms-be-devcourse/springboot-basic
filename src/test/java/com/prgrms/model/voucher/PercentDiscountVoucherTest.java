package com.prgrms.model.voucher;

import com.prgrms.model.order.OrderItem;
import com.prgrms.model.voucher.dto.discount.PercentDiscount;
import com.prgrms.model.voucher.dto.price.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PercentDiscountVoucherTest {
    private OrderItem orderItem;
    private int id = 1;

    @BeforeEach
    public void setUp() {
        orderItem = new OrderItem(1, 1000, 1);
    }

    @Test
    @DisplayName("할인된 금액이 제대로 나오는지 확인한다.")
    public void discountPrice_DiscountedPrice_Equal() {
        //given
        Voucher createdVoucher = new PercentDiscountVoucher(id, new PercentDiscount(20), VoucherType.PERCENT_DISCOUNT_VOUCHER);

        //when
        Price discountedPrice = createdVoucher.discountPrice(orderItem);

        //then
        assertThat(discountedPrice.getValue()).isEqualTo(800);
    }
}
