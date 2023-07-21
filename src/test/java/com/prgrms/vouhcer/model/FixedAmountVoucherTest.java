package com.prgrms.vouhcer.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prgrms.order.model.OrderItem;
import com.prgrms.order.model.Price;
import com.prgrms.voucher.model.FixedAmountVoucher;
import com.prgrms.voucher.model.Voucher;
import com.prgrms.voucher.model.VoucherType;
import com.prgrms.voucher.model.discount.FixedDiscount;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
    @DisplayName("고정 할인 바우처가 적용된 할인된 금액이 예상값과 같게 나온다.")
    void discountPrice_DiscountedPrice_Equal() {
        //given
        Voucher createdVoucher = new FixedAmountVoucher(voucherId, new FixedDiscount(20),
                VoucherType.FIXED_AMOUNT_VOUCHER, LocalDateTime.now());

        //when
        Price discountedPrice = createdVoucher.discountPrice(orderItem);

        //then
        assertThat(discountedPrice.cost()).isEqualTo(980);
    }

    @Test
    @DisplayName("할인ㄷ된 금액이 원가보다 커서 할인된 금액이 음수가 나오는 경우 예외를 던진다.")
    void discountPrice_NegativeDiscountedPrice_ThrowsException() {
        //given
        Voucher createdVoucher = new FixedAmountVoucher(voucherId, new FixedDiscount(2000),
                VoucherType.FIXED_AMOUNT_VOUCHER, LocalDateTime.now());

        //when_then
        assertThatThrownBy(() -> createdVoucher.discountPrice(orderItem))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
