package org.prgrms.kdt.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.prgrms.kdt.domain.order.Order;
import org.prgrms.kdt.domain.order.OrderItem;
import org.prgrms.kdt.domain.order.OrderStatus;
import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.repository.order.MemoryOrderRepository;
import org.prgrms.kdt.repository.order.OrderRepository;
import org.prgrms.kdt.repository.voucher.MemoryVoucherRepository;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Test
    @DisplayName("오더가 생성되어야 한다. by stub")
    public void createOrderByStub() throws Exception {
        // given
        MemoryVoucherRepository voucherRepository = new MemoryVoucherRepository();
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        OrderService orderService = new OrderService(new VoucherService(voucherRepository), new MemoryOrderRepository());

        voucherRepository.insert(voucher);

        // when
        Order order = orderService.createOrder(UUID.randomUUID(), List.of(new OrderItem(UUID.randomUUID(), 200, 1)), voucher.getVoucherId());

        // then
        assertThat(order.totalAmount()).isEqualTo(100L);
        assertThat(order.getVoucher()).isNotEmpty();
        assertThat(order.getVoucher().get().getVoucherId()).isEqualTo(voucher.getVoucherId());
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @Test
    @DisplayName("오더가 생성되어야 한다. by mock")
    public void createOrderByMock() throws Exception {
        // given
        VoucherService voucherService = mock(VoucherService.class);
        OrderRepository orderRepository = mock(OrderRepository.class);
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);

        OrderService orderService = new OrderService(voucherService, orderRepository);

        when(voucherService.getVoucher(fixedAmountVoucher.getVoucherId())).thenReturn(fixedAmountVoucher);

        // when
        Order order = orderService.createOrder(
                UUID.randomUUID(),
                List.of(new OrderItem(UUID.randomUUID(), 200, 1)),
                fixedAmountVoucher.getVoucherId());

        // then
        assertThat(order.totalAmount()).isEqualTo(100L);
        assertThat(order.getVoucher().isEmpty()).isEqualTo(false);
        InOrder inOrder = inOrder(voucherService, orderRepository);

        inOrder.verify(voucherService).getVoucher(fixedAmountVoucher.getVoucherId());
        inOrder.verify(orderRepository).insert(order);
        inOrder.verify(voucherService).useVoucher(fixedAmountVoucher);

    }

}