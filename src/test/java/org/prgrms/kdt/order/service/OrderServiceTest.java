package org.prgrms.kdt.order.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.order.domain.Order;
import org.prgrms.kdt.order.domain.OrderItem;
import org.prgrms.kdt.order.domain.OrderStatus;
import org.prgrms.kdt.order.repository.OrderRepository;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.repository.MemoryVoucherRepository;
import org.prgrms.kdt.voucher.service.VoucherService;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    class OrderRepositoryStub implements OrderRepository {
        @Override
        public Order insert(Order order) {
            return null;
        }
    }

    @Test
    @DisplayName("오더가 생성되어야한다. (stub)")
    void createOrder() {
        //given
        MemoryVoucherRepository voucherRepository = new MemoryVoucherRepository();
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        voucherRepository.create(fixedAmountVoucher);
        var sut = new OrderService(new VoucherService(voucherRepository), new OrderRepositoryStub());

        //when
        Order order = sut.createOrder(UUID.randomUUID(), List.of(new OrderItem(UUID.randomUUID(), 200, 1)), fixedAmountVoucher.getVoucherId());

        //then
        assertThat(order.totalAmount(), is(100L));
        assertThat(order.getVoucher().isEmpty(), is(false));
        assertThat(order.getVoucher().get(), is(false));
        assertThat(order.getOrderStatus(), is(OrderStatus.ACCEPTED));
    }

    @Test
    @DisplayName("오더가 생성되어야한다. (mock)")
    void createOrderMock() {
        //given
        var voucherServiceMock = mock(VoucherService.class);
        var orderRepositoryMock = mock(OrderRepository.class);
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        when(voucherServiceMock.getVoucher(fixedAmountVoucher.getVoucherId())).thenReturn(fixedAmountVoucher);
        var sut = new OrderService(voucherServiceMock, orderRepositoryMock);

        //when
        Order order = sut.createOrder(
                UUID.randomUUID(),
                List.of(new OrderItem(UUID.randomUUID(), 200, 1)),
                fixedAmountVoucher.getVoucherId());

        //then
        assertThat(order.totalAmount(), is(100L));
        assertThat(order.getVoucher().isEmpty(), is(false));
//        var inOrder = inOrder(voucherServiceMock);
        verify(voucherServiceMock).getVoucher(fixedAmountVoucher.getVoucherId());//해당 메소드가 호출 되었는지를 검증
        verify(orderRepositoryMock).insert(order);
        verify(voucherServiceMock).useVoucher(fixedAmountVoucher);


    }
}