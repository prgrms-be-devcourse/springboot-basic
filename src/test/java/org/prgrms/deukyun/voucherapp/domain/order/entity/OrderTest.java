package org.prgrms.deukyun.voucherapp.domain.order.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.prgrms.deukyun.voucherapp.domain.voucher.entity.FixedAmountDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.entity.PercentDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.entity.Voucher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderTest {

    UUID customerId;
    List<OrderItem> orderItems;
    long totalPrice;
    Voucher voucher;

    @BeforeEach
    void setup() {
        //customerId
        customerId = UUID.randomUUID();

        //orderItems - total price 3000
        long price1 = 1000L;
        long price2 = 2000L;
        orderItems = orderItemsOfSizeTwoWithPrices(price1, price2);
        totalPrice = price1 + price2;

        //voucher - default voucher : no discount logic
        voucher = null;
    }

    private PercentDiscountVoucher percentDiscountVoucherWithPercent(long percent) {
        return new PercentDiscountVoucher(percent);
    }

    private FixedAmountDiscountVoucher fixedAmountDiscountVoucherWithAmount(long amount) {
        return new FixedAmountDiscountVoucher(amount);
    }

    private List<OrderItem> orderItemsOfSizeTwoWithPrices(long price1, long price2) {
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem mockOrderItem1 = mock(OrderItem.class);
        OrderItem mockOrderItem2 = mock(OrderItem.class);
        when(mockOrderItem1.getTotalPrice()).thenReturn(price1);
        when(mockOrderItem2.getTotalPrice()).thenReturn(price2);
        orderItems.add(mockOrderItem1);
        orderItems.add(mockOrderItem2);
        return orderItems;
    }

    @Nested
    class createOrderTest {

        @Test
        void givenTwoOrderItems_whenCallCreateOrder_thenOrderCreated() {
            //action
            Order order = Order.createOrder(customerId, orderItems);

            //assert
            assertThat(order).isNotNull();
        }

        @Test
        void givenNullCustomerId_whenCallCreateOrder_thenThrowsIllegalArgumentException() {
            //setup
            customerId = null;

            //assert throws
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Order.createOrder(customerId, orderItems));
        }

        @ParameterizedTest
        @NullAndEmptySource
        void givenNullAndEmptyOrderItems_whenConstructOrder_thenThrowsIllegalArgumentException(List<OrderItem> orderItems) {
            //assert throws
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Order.createOrder(customerId, orderItems));
        }
    }

    @Nested
    class createOrderWithVoucherTest {

        @Test
        void givenFixedAmountDiscountVoucher_whenCallCreateOrderWithVoucher_thenOrderCreated() {
            //setup
            voucher = fixedAmountDiscountVoucherWithAmount(2000L);

            //action
            Order order = Order.createOrderWithVoucher(customerId, orderItems, voucher);

            //assert
            assertThat(order).isNotNull();
        }

        @Test
        void givenPercentDiscountVoucher_whenCallCreateOrderWithVoucher_thenOrderCreated() {
            //setup
            voucher = percentDiscountVoucherWithPercent(20L);

            //action
            Order order = Order.createOrderWithVoucher(customerId, orderItems, voucher);

            //assert
            assertThat(order).isNotNull();
        }

        @Test
        void givenNullCustomerId_whenCallCreateOrderWithVoucher_thenThrowsIllegalArgumentException() {
            //setup
            customerId = null;
            voucher = fixedAmountDiscountVoucherWithAmount(2000L);

            //assert throws
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Order.createOrderWithVoucher(customerId, orderItems, voucher));
        }

        @ParameterizedTest
        @NullAndEmptySource
        void givenNullAndEmptyOrderItems_whenCallCreateOrderWithVoucher_thenThrowsIllegalArgumentException(List<OrderItem> orderItems) {
            //setup
            voucher = fixedAmountDiscountVoucherWithAmount(2000L);

            //assert throws
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Order.createOrderWithVoucher(customerId, orderItems, voucher));
        }
    }

    @Nested
    class getOrderStatusTest {

        @Test
        void givenCreatedOrder_whenGetOrderStatus_thenIsOrderStatusAccepted() {
            //setup
            Order order = Order.createOrder(customerId, orderItems);

            //action
            OrderStatus orderStatus = order.getOrderStatus();

            //assert
            assertThat(orderStatus).isEqualTo(OrderStatus.ACCEPTED);
        }
    }

    @Nested
    class totalPriceTest {

        @Test
        void givenOrderWithoutVoucher_whenCallTotalPrice_thenReturnsNaiveTotalPrice() {
            //setup
            Order order = Order.createOrder(customerId, orderItems);

            //action
            long totalPrice = order.totalPrice();

            //assert
            assertThat(totalPrice).isEqualTo(totalPrice);
        }


        @Test
        void givenFixedAmountDiscountVoucher_whenCallTotalPrice_thenReturnsFixedAmountDiscountedPrice() {
            //setup
            long amount = 20L;
            voucher = fixedAmountDiscountVoucherWithAmount(amount);
            Order order = Order.createOrderWithVoucher(customerId, orderItems, voucher);

            //action
            long orderTotalPrice = order.totalPrice();

            //assert
            assertThat(orderTotalPrice).isEqualTo(totalPrice - amount);
        }

        @Test
        void givenPercentDiscountVoucher_whenCallTotalPrice_thenReturnsPercentDiscountedPrice() {
            //setup
            long percent = 20L;
            voucher = percentDiscountVoucherWithPercent(percent);
            Order order = Order.createOrderWithVoucher(customerId, orderItems, voucher);

            //action
            long orderTotalPrice = order.totalPrice();

            //assert
            assertThat(orderTotalPrice).isEqualTo(totalPrice * (100 - percent) / 100);
        }
    }
}