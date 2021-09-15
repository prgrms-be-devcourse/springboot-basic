package com.programmers.kdtspringorder.order;

import com.programmers.kdtspringorder.voucher.service.VoucherService;
import com.programmers.kdtspringorder.voucher.domain.FixedAmountVoucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Test
    @DisplayName("오더가 생성돼야 한다.")
    public void createOrderByMock() throws Exception{
        // Given
        VoucherService voucherServiceMock = mock(VoucherService.class);
        OrderRepository orderRepositoryMock = mock(OrderRepository.class);
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100L);
        when(voucherServiceMock.findByID(fixedAmountVoucher.getVoucherId())).thenReturn(fixedAmountVoucher);

        OrderService sut = new OrderService(voucherServiceMock, orderRepositoryMock);

        // When
        Order order = sut.createOrder(
                UUID.randomUUID(),
                List.of(new OrderItem(UUID.randomUUID(), 200, 1)),
                fixedAmountVoucher.getVoucherId()
        );

        // Then
        assertThat(order.totalAmount(), is(100L));
        assertThat(order.getVoucher().isEmpty(), is(false));
        InOrder inOrder = inOrder(voucherServiceMock, orderRepositoryMock);

        inOrder.verify(voucherServiceMock).findByID(fixedAmountVoucher.getVoucherId());
        inOrder.verify(voucherServiceMock).useVoucher(fixedAmountVoucher);
        inOrder.verify(orderRepositoryMock).insert(order);

    }
}
