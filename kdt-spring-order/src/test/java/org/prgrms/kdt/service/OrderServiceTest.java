package org.prgrms.kdt.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.prgrms.kdt.order.Order;
import org.prgrms.kdt.order.OrderItem;
import org.prgrms.kdt.order.OrderStatus;
import org.prgrms.kdt.repository.MemoryVoucherRepository;
import org.prgrms.kdt.repository.OrderRepository;
import org.prgrms.kdt.voucher.FixedAmountVoucher;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    class OrderRepositoryStub implements OrderRepository{
        @Override
        public Order insert(Order order) {
            return null;
        }
    }


    @Test
    void createOrder(){
//        Given
        var voucherRepository = new MemoryVoucherRepository();
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        voucherRepository.insert(fixedAmountVoucher);
        var sut = new OrderService(new VoucherService(voucherRepository), new OrderRepositoryStub());

//        When
        Order order = sut.createOrder(UUID.randomUUID(), List.of(new OrderItem(UUID.randomUUID(), 200, 1)), fixedAmountVoucher.getVoucherId());

//        Then
        assertThat(order.totalAmount(), is(100L));
        assertThat(order.getVoucher().isEmpty(), is(false));
        assertThat(order.getVoucher().get().getVoucherId(), is(fixedAmountVoucher.getVoucherId()));
        assertThat(order.getOrderStatus(), is(OrderStatus.ACCEPTED));
    }

    @Test
    @DisplayName("오더가 생성되야한다. (mock)")
    void createOrderByMock(){
//        Given
        VoucherService voucherServiceMock = mock(VoucherService.class);
        OrderRepository orderRepositoryMock = mock(OrderRepository.class);
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        when(voucherServiceMock.getVoucher(fixedAmountVoucher.getVoucherId())).thenReturn(fixedAmountVoucher);
        OrderService sut = new OrderService(voucherServiceMock, orderRepositoryMock);

//        When
        Order order = sut.createOrder(UUID.randomUUID(), List.of(new OrderItem(UUID.randomUUID(), 200, 1)), fixedAmountVoucher.getVoucherId());

//        Then
        assertThat(order.totalAmount(), is(100L));
        assertThat(order.getVoucher().isEmpty(), is(false));
        InOrder inOrder = inOrder(voucherServiceMock, orderRepositoryMock);
        inOrder.verify(voucherServiceMock).getVoucher(fixedAmountVoucher.getVoucherId());
        inOrder.verify(orderRepositoryMock).insert(order);
        inOrder.verify(voucherServiceMock).useVoucher(fixedAmountVoucher);
    }

}