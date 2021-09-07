package org.prgrms.kdt.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.prgrms.kdt.domain.order.Order;
import org.prgrms.kdt.domain.order.OrderItem;
import org.prgrms.kdt.domain.order.OrderStatus;
import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.repository.MemoryVoucherRepository;
import org.prgrms.kdt.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    class OrderRepositoryStub implements OrderRepository{

        @Override
        public Order insert(Order order) {
            return null;
        }
    }

    @Test
    @DisplayName("Order가 생성되어야 한다(Stub)")
    void createOrder(){
        //Given
        MemoryVoucherRepository voucherRepository = new MemoryVoucherRepository();
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        voucherRepository.insert(voucher);
        OrderService sut = new OrderService(new VoucherService(voucherRepository), new OrderRepositoryStub());

        //When
        Order order = sut.createOrder(UUID.randomUUID(),
                                    List.of(new OrderItem(UUID.randomUUID(), 200, 1)),
                                    voucher.getVoucherId());

        //Then
        assertThat(order.totalAmount(), is(100L));
        assertThat(order.getVoucher().isEmpty(), is(false));
        assertThat(order.getVoucher().get().getVoucherId(), is(voucher.getVoucherId()));
        assertThat(order.getOrderStatus(), is(OrderStatus.ACCEPTED));
    }

    @Test
    @DisplayName("Order가 생성되어야 한다(Stub)")
    void createOrderByMock(){
        //Given
        VoucherService voucherServiceMock = mock(VoucherService.class);
        OrderRepository orderRepositoryMock = mock(OrderRepository.class);
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        when(voucherServiceMock.getVoucher(voucher.getVoucherId())).thenReturn(voucher);

        OrderService sut = new OrderService(voucherServiceMock, orderRepositoryMock);

        //When
        Order order = sut.createOrder(UUID.randomUUID(),
                                        List.of(new OrderItem(UUID.randomUUID(), 200, 1)),
                                        voucher.getVoucherId());

        //Then

        //상태 검증
        assertThat(order.totalAmount(), is(100L));
        assertThat(order.getVoucher().isEmpty(), is(false));

        //행위 검증(객체의 메소드가 호출되었는지 확인) - 순서 상관 없음
        //verify(voucherServiceMock).getVoucher(voucher.getVoucherId());
        //verify(orderRepositoryMock).insert(order);
        //verify(voucherServiceMock).useVoucher(voucher);

        //행위 검증(객체의 메소드가 호출되었는지 확인) - 순서 상관 있음
        InOrder inOrder = inOrder(voucherServiceMock, orderRepositoryMock);
        inOrder.verify(voucherServiceMock).getVoucher(voucher.getVoucherId());
        inOrder.verify(orderRepositoryMock).insert(order);
        inOrder.verify(voucherServiceMock).useVoucher(voucher);
    }

}