package org.prgrms.kdt.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.prgrms.kdt.domain.order.Order;
import org.prgrms.kdt.domain.order.OrderItem;
import org.prgrms.kdt.domain.order.OrderStatus;
import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.repository.voucher.MemoryVoucherRepository;
import org.prgrms.kdt.repository.order.OrderRepository;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    class OrderRepositoryStub implements OrderRepository {
        @Override
        public Order insert(Order order) {
            return null;
        }
    }

    @Test
    @DisplayName("오더가 생성되어야 한다. Stub version")
    void createOrder() {
        //Given - 상황이주어졌을때
        MemoryVoucherRepository memoryVoucherRepository = new MemoryVoucherRepository();
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.fromString("882452fe-3aed-4974-91bf-16074681060b"), UUID.randomUUID(), 100, VoucherType.FIXED);
        memoryVoucherRepository.save(fixedAmountVoucher);

        //오더 상태를 검증하기위해서 오더레포지토리가 필요한데,
        //오더 레포지토리를 실제로 사용하는건 아니니까 가짜 객체인 OrderRepositoryStub 객체를 만들어주는건가?
        OrderService sut= new OrderService(new VoucherService(memoryVoucherRepository), new OrderRepositoryStub());

        //When - 상황에 맞는 메서드가 호출된다.
        Order order = sut.createOrder(UUID.randomUUID(), List.of(new OrderItem(UUID.randomUUID(), 200, 1)), fixedAmountVoucher.getVoucherId());

        //Then - order 상태를 검증한다.(상태에 대한 검증)
        assertThat(order.totalAmount(), is(100L));
        assertThat(order.getVoucher().isEmpty(), is(false));
        assertThat(order.getVoucher().get().getVoucherId(), is(fixedAmountVoucher.getVoucherId()));
        assertThat(order.getOrderStatus(), is(OrderStatus.ACCEPTED));
    }


    @Test
    @DisplayName("오더가 생성되어야 한다. Mock version")
    void createOrderByMock() {
        //Given
        VoucherService voucherServiceMock = mock(VoucherService.class);
        OrderRepository orderRepositoryMock = mock(OrderRepository.class);
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.fromString("882452fe-3aed-4974-91bf-16074681060b"), UUID.randomUUID(), 100, VoucherType.FIXED);
        when(voucherServiceMock.getVoucher(fixedAmountVoucher.getVoucherId())).thenReturn(fixedAmountVoucher);
        OrderService sut = new OrderService(voucherServiceMock, orderRepositoryMock);

        //When
        Order order = sut.createOrder(
                UUID.randomUUID(),
                List.of(new OrderItem(UUID.randomUUID(), 200, 1)),
                fixedAmountVoucher.getVoucherId());

        //Then - voucherServiceMock, orderRepositoryMock 객체에 대해서 어떠한 메서드가 정상적으로 호출되어지는지 검증해야한다.(행위에 대한 검증)

        //메서드 호출 순서 확인
        InOrder inOrder = inOrder(voucherServiceMock);
        inOrder.verify(voucherServiceMock).getVoucher(fixedAmountVoucher.getVoucherId());
        //inOrder.verify(voucherServiceMock).useVoucher(fixedAmountVoucher);
        verify(orderRepositoryMock).insert(order);

        //메서드 호출 순서 확인안함
        verify(voucherServiceMock).getVoucher(fixedAmountVoucher.getVoucherId());
        verify(orderRepositoryMock).insert(order);

        //메서드 호출여부
        assertThat(order.totalAmount(), is(100L));
        assertThat(order.totalAmount(), is(100L));
        //verify(voucherServiceMock).useVoucher(fixedAmountVoucher);

    }
}