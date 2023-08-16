package com.prgrms.vouhcer.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.prgrms.order.model.OrderItem;
import com.prgrms.order.model.Price;
import com.prgrms.voucher.model.voucher.PercentDiscountVoucher;
import com.prgrms.voucher.model.voucher.Voucher;
import com.prgrms.voucher.model.VoucherType;
import com.prgrms.voucher.model.discount.PercentDiscount;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PercentDiscountVoucherTest {

    final String voucherId = "1";
    final String orderId = "1";
    final int quantity = 1;

    OrderItem orderItem;
    Price productPrice = new Price(1000);

    @BeforeEach
    void setUp() {
        orderItem = new OrderItem(orderId, productPrice, quantity);
    }

    @Test
    @DisplayName("할인율 바우처가 적용된 할인된 금액이 예상값과 같게 나온다.")
    void discountPrice_DiscountedPrice_Equal() {
        //given
        Voucher createdVoucher = new PercentDiscountVoucher(voucherId, new PercentDiscount(20),
                VoucherType.PERCENT_DISCOUNT_VOUCHER, LocalDateTime.now());

        //when
        Price discountedPrice = createdVoucher.discountPrice(orderItem);

        //then
        assertThat(discountedPrice.cost()).isEqualTo(800);
    }

}
