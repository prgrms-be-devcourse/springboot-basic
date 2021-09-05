package org.prgms.w3d1.model.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import org.prgms.w3d1.model.voucher.FixedAmountVoucher;
import org.prgms.w3d1.model.voucher.VoucherService;
import org.prgms.w3d1.repository.OrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    /*
        테스트 : createOrder(args : 2) 메서드가 호출될 때 내부에서 일어나는 행위 검증
        Given : 매개변수의 mock 객체 2개를 넣어준다.
        when : createOrder 메서드가 호출되면
        then : 추가로 호출되는 각각의 메서드를 확인한다.
     */
    @Test
    @DisplayName("order가 생성되야한다.")
    void testCreateOrderArgsTwo() {
        // Given
        var voucherServiceMock = mock(VoucherService.class);
        var orderRepositoryMock = mock(OrderRepository.class);
        var sut = new OrderService(voucherServiceMock, orderRepositoryMock);

        // When
        var order = sut.createOrder(UUID.randomUUID(),
                            List.of(new OrderItem(UUID.randomUUID(),1000L, 1)));

        // then
        verify(orderRepositoryMock).insert(order);

    }

    /*
        테스트 : createOrder(args : 3) 메서드가 호출될 때 내부에서 일어나는 행위 검증
        Given : 매개변수의 mock 객체 2개를 넣어준다.
        when : createOrder 메서드가 호출되면
        then : 추가로 호출되는 각각의 메서드를 확인한다.
     */
    @Test
    void testCreateOrderArgsThree() {
        // Given
        var voucherServiceMock = mock(VoucherService.class);
        var orderRepositoryMock = mock(OrderRepository.class);
        var voucher = FixedAmountVoucher.of(UUID.randomUUID(), 100L);
        when(voucherServiceMock.getVoucher(voucher.getVoucherId())).thenReturn(voucher);
        var sut = new OrderService(voucherServiceMock, orderRepositoryMock);

        // When
        var order = sut.createOrder(UUID.randomUUID(),
            List.of(new OrderItem(UUID.randomUUID(),1000L, 1)),
            voucher.getVoucherId());

        // then
        var inOrder = inOrder(voucherServiceMock, orderRepositoryMock);
        inOrder.verify(voucherServiceMock).getVoucher(voucher.getVoucherId());
        inOrder.verify(orderRepositoryMock).insert(order);
        inOrder.verify(voucherServiceMock).useVoucher(voucher);
    }
}