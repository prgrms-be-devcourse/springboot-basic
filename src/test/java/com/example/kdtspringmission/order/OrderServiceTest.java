package com.example.kdtspringmission.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.example.kdtspringmission.voucher.domain.FixedAmountVoucher;
import com.example.kdtspringmission.voucher.repository.MemoryVoucherRepository;
import com.example.kdtspringmission.voucher.service.VoucherService;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

class OrderServiceTest {

    class OrderRepositoryStub implements OrderRepository {

        @Override
        public Order insert(Order order) {
            return null;
        }
    }


    @Test
    @DisplayName("주문이 생성되어야 한다 - stub")
    void testCreateOrderByStub() {
        // given
        MemoryVoucherRepository voucherRepository = new MemoryVoucherRepository();
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100L);
        voucherRepository.insert(voucher);
        OrderService sut = new OrderService(new VoucherService(voucherRepository),
            new OrderRepositoryStub());

        // when
        Order order = sut
            .createOrder(UUID.randomUUID(), List.of(new OrderItem(UUID.randomUUID(), 200, 1)),
                voucher.getId());

        assertThat(order.totalAmount()).isEqualTo(100L);
        assertThat(order.getVoucher().isEmpty()).isFalse();
        assertThat(order.getVoucher().get().getId()).isEqualTo(voucher.getId());
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @Test
    @DisplayName("주문이 생성되어야 한다 - mock")
    @Disabled
    void testCreateOrderByMock() {
        // given
        VoucherService voucherServiceMock = mock(VoucherService.class);
        OrderRepository orderRepositoryMock = mock(OrderRepository.class);
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100L);
        when(voucherServiceMock.getVoucher(voucher.getId())).thenReturn(voucher);
        OrderService sut = new OrderService(voucherServiceMock, orderRepositoryMock);

        // when
        Order order = sut.createOrder(
            UUID.randomUUID(),
            List.of(new OrderItem(UUID.randomUUID(), 200, 1)),
            voucher.getId());

        // then
        assertThat(order.totalAmount()).isEqualTo(100L);
        assertThat(order.getVoucher().isEmpty()).isFalse();
        InOrder inOrder = inOrder(voucherServiceMock);
        inOrder.verify(voucherServiceMock).getVoucher(voucher.getId());
        inOrder.verify(orderRepositoryMock).insert(order);
        verify(voucherServiceMock).useVoucher(voucher);

    }
}
