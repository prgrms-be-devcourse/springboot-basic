package org.prgrms.deukyun.voucherapp.order.entity;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.prgrms.deukyun.voucherapp.voucher.entity.Voucher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderTest {

    UUID customerId = UUID.randomUUID();

    @Nested
    class constructorTest {

        @Test
        void givenTwoOrderItemsAnyVoucher_whenConstructOrder_thenOrderCreated() {
            //setup
            Voucher mockVoucher = mock(Voucher.class);
            List<OrderItem> orderItems = orderItemsOfSizeTwo();

            //action
            Order order = new Order(customerId, orderItems, mockVoucher);

            //assert
            assertThat(order).isNotNull();
        }

        @Test
        void givenNullVoucher_whenConstructOrder_thenOrderCreated() {
            //setup
            List<OrderItem> orderItems = orderItemsOfSizeTwo();
            Voucher voucher = null;

            //action
            Order order = new Order(customerId, orderItems, voucher);

            //assert
            assertThat(order).isNotNull();
        }

        @Test
        void givenNullCustomerId_whenConstructOrder_thenThrowIllegalArgumentException() {
            //setup
            customerId = null;
            List<OrderItem> mockOrderItems = orderItemsOfSizeTwo();
            Voucher mockVoucher = mock(Voucher.class);

            //assert throws
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> new Order(customerId, mockOrderItems, mockVoucher));
        }

        @Test
        void givenNullOrderItems_whenConstructOrder_thenThrowIllegalArgumentException() {
            //setup
            List<OrderItem> mockOrderItems = null;
            Voucher mockVoucher = mock(Voucher.class);

            //assert throws
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> new Order(customerId, mockOrderItems, mockVoucher));
        }

        @Test
        void givenEmptyOrderItems_whenConstructOrder_thenThrowIllegalArgumentException() {
            //setup
            List<OrderItem> mockOrderItems = new ArrayList<>();
            Voucher mockVoucher = mock(Voucher.class);

            //assert throws
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> new Order(customerId, mockOrderItems, mockVoucher));
        }
    }

    @Test
    void givenCreatedOrder_whenGetOrderStatus_thenIsOrderStatusAccepted() {
        //setup
        Order order = new Order(customerId, orderItemsOfSizeTwo(), mock(Voucher.class));

        //action
        OrderStatus orderStatus = order.getOrderStatus();

        //assert
        assertThat(orderStatus).isEqualTo(OrderStatus.ACCEPTED);
    }

    @Nested
    class totalPriceTest {

        @Test
        void givenOneOrderItemNullVoucher_whenCallTotalPrice_thenReturnsTotalPriceOfGivenOrderItems() {
            //setup
            List<OrderItem> orderItems = new ArrayList<>();
            OrderItem orderItem = mock(OrderItem.class);
            when(orderItem.getTotalPrice()).thenReturn(2000L);
            orderItems.add(orderItem);
            Order order = new Order(customerId, orderItems, null);

            //action
            long orderTotalPrice = order.totalPrice();

            //assert
            assertThat(orderTotalPrice).isEqualTo(2000L);
        }
    }

    private List<OrderItem> orderItemsOfSizeTwo() {
        return Arrays.asList(mock(OrderItem.class), mock(OrderItem.class));
    }
}