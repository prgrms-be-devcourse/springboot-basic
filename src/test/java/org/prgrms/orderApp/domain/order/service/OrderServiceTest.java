package org.prgrms.orderApp.domain.order.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.prgrms.orderApp.domain.order.model.Order;
import org.prgrms.orderApp.domain.order.model.OrderItem;
import org.prgrms.orderApp.domain.order.model.OrderStatus;
import org.prgrms.orderApp.domain.order.repository.OrderRepository;
import org.prgrms.orderApp.domain.voucher.model.FixedAmountVoucher;
import org.prgrms.orderApp.domain.voucher.service.VoucherService;
import org.prgrms.orderApp.infrastructure.impl.TempVoucherRepository;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;
class OrderServiceTest {

    class OrderRepositoryStub implements OrderRepository {

        @Override
        public Map<UUID, Order> selectAll() {
            return null;
        }

        @Override
        public Order insert(Order order) throws IOException {
            return null;
        }

        @Override
        public Optional<Order> selectById(UUID orderId) {
            return Optional.empty();
        }
    }

    @Test
    void createOrder() throws IOException {
        // Given
        var voucherRepository = new TempVoucherRepository();
        var fixedAmoutVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        voucherRepository.save(fixedAmoutVoucher);
        var sut = new OrderService(new VoucherService(voucherRepository), new OrderRepositoryStub());

        // When
        var order = sut.createOrder(UUID.randomUUID(), List.of(new OrderItem(UUID.randomUUID(), 200, 1)), fixedAmoutVoucher.getVoucherId());

        // Then
        assertThat(order.totalAmount(), equalTo(100L));
        assertThat(order.getVoucher().isEmpty(), is(false));
        assertThat(order.getVoucher().get().getVoucherId(), is(fixedAmoutVoucher.getVoucherId()));
        assertThat(order.getOrderStatus(), is(OrderStatus.ACCEPTED));
    }
    @Test
    @DisplayName("오더가 생성되어야 한다. (stub)")
    void createOrderMock() throws IOException {
        // Given
        var voucherServiceMock = mock(VoucherService.class);
        var orderRepositoryMock = mock(OrderRepository.class);
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        when(voucherServiceMock.getVoucher(fixedAmountVoucher.getVoucherId())).thenReturn(fixedAmountVoucher);
        var sut = new OrderService(voucherServiceMock, orderRepositoryMock);

        // When
        var order = sut.createOrder(
                UUID.randomUUID(),
                List.of(new OrderItem(UUID.randomUUID(), 200, 1)),
                fixedAmountVoucher.getVoucherId());

        // Then
        assertThat(order.totalAmount(), is(100L));
        assertThat(order.getVoucher().isEmpty(), is(false));
        verify(voucherServiceMock).getVoucher(fixedAmountVoucher.getVoucherId());
        verify(orderRepositoryMock).insert(order);
        verify(voucherServiceMock).useVoucher(fixedAmountVoucher);

        var inOrder = inOrder(voucherServiceMock, orderRepositoryMock);
        inOrder.verify(voucherServiceMock).getVoucher(fixedAmountVoucher.getVoucherId());
        inOrder.verify(orderRepositoryMock).insert(order);
        inOrder.verify(voucherServiceMock).useVoucher(fixedAmountVoucher);
    }

}