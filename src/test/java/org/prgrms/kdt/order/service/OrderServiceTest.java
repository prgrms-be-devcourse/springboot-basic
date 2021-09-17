package org.prgrms.kdt.order.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.order.OrderItem;
import org.prgrms.kdt.order.OrderStatus;
import org.prgrms.kdt.order.domain.Order;
import org.prgrms.kdt.order.repository.OrderRepository;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.repository.MemoryVoucherRepository;
import org.prgrms.kdt.voucher.service.VoucherService;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

/**
 * Unit Test Mocking
 * 테스트 더블 : 객체와 연관된 객체를 사용하기가 어렵고 모호할 때 대신해 줄 수 있는 객체
 * 테스트 더블 = [ Dummy, Fake, Stub, Spy, Mock ]
 * <p>
 * Mock(행위) : 호출에 대한 기대를 명세하고 내용에 따라 동작하도록 프로그래밍 된 객체
 * <p>
 * Stub(결과/상태) : 테스트를 위해 프로그래밍된 내용에 대해서만 준비된 결과를 제공하는 객체
 */
class OrderServiceTest {

    @Test
    @DisplayName("오더가 생성되어야 한다. (Stub)")
    void createOrderByStub() {
//        Given : 상
        MemoryVoucherRepository voucherRepository = new MemoryVoucherRepository();
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        voucherRepository.insert(fixedAmountVoucher);
        var sut = new OrderService(new VoucherService(voucherRepository), new OrderRepositoryStub());

//        WHEN : method
        var order = sut.createOrder(UUID.randomUUID(), List.of(new OrderItem(UUID.randomUUID(), 200, 1)), fixedAmountVoucher.getVoucherId());

//        THEN : 검증 확인
        assertThat(order.totalAmount(), is(100L));

        assertThat(order.getVoucher().isEmpty(), is(false));
        assertThat(order.getVoucher().get().getVoucherId(), is(fixedAmountVoucher.getVoucherId()));

        assertThat(order.getOrderStatus(), is(OrderStatus.ACCEPTED));
    }

    /**
     * Mock Objcet의 지원을 받아 별도의 코드를 추가하지않고 method 호출 여부를 간단하게 검사할 수 있었고
     * 어떻게 검사가 수행이 되는 지를 veryfy(.. 주저리주저리)를 통해 확인할 수 있습니다.
     * 우리가 얘가 순서가 호출이 되는 여부에 집중이 되었는데 , 특정한 순서를 보장해서 호출되어져야 할때 mockito에서 inorder라는 걸 이용하면 됩니다.
     */
    @Test
    @DisplayName("오더가 생성되어야 한다. (Mock)")
    // 행위!! 집중!!
    void createOrderByMock() {
//        Given
        var voucherServiceMock = mock(VoucherService.class); // mock class 생성 -> 행위에 집중해야함.
        var orderRepositoryMock = mock(OrderRepository.class); // mock class 생성 -> 행위에 집중해야함
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100); // 얘만 지원하는 거임.

        // Mock은 when으로 기술한 이 부분만 동작하는 거임.
        when(voucherServiceMock.getVoucher(fixedAmountVoucher.getVoucherId()))
                .thenReturn(fixedAmountVoucher);
        var sut = new OrderService(voucherServiceMock, orderRepositoryMock);

//        WHEN : method
        var order = sut.createOrder(
                UUID.randomUUID(),
                List.of(new OrderItem(UUID.randomUUID(), 200, 1)),
                fixedAmountVoucher.getVoucherId());

//        THEN
        assertThat(order.totalAmount(), is(100L));
        assertThat(order.getVoucher().isEmpty(), is(false));
        // 행위를 검증하는 거임.
        var inOrder = inOrder(voucherServiceMock, orderRepositoryMock);// voucherService의 method가 어떤 순서에 의해서 호출되어지는지 확인할 수 있습니다.
        inOrder.verify(voucherServiceMock).getVoucher(fixedAmountVoucher.getVoucherId()); // voucherServiceMock객체가 getVoucher()가 요청되어 졌다. 뭘로? fixedAmountVoucher의 getVoucherId()로.
        inOrder.verify(orderRepositoryMock).insert(order); // order가 추가(행위)됐는지 verfy 함.
        inOrder.verify(voucherServiceMock).useVoucher(fixedAmountVoucher); // voucherService에서 useVoucher도 사용했다는 것도 처리가 되어야 합니다.
    }

    class OrderRepositoryStub implements OrderRepository {

        @Override
        public Order insert(Order order) {
            return null;
        }
    }

}