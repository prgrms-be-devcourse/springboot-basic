package org.prgrms.kdt.order;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.repository.VoucherMemoryRepository;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.prgrms.kdt.wallet.WalletJdbcRepository;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
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
    @DisplayName("오더가 생성되야한다. (stub)")
    void createOrder() {
        // Given
        var voucherRepository = new VoucherMemoryRepository();
        var dataSource = DataSourceBuilder.create().type(HikariDataSource.class).build();
        var jdbcTemplate = new JdbcTemplate(dataSource);
        var walletRepository = new WalletJdbcRepository(dataSource, jdbcTemplate);
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100, LocalDateTime.now());
        VoucherService voucherService = new VoucherService(voucherRepository, walletRepository);

        voucherRepository.save(fixedAmountVoucher);
        var sut = new OrderService(voucherService, new OrderRepositoryStub());

        // When
        var order = sut.createOrder(UUID.randomUUID(), List.of(new OrderItem(UUID.randomUUID(), 200, 1)), fixedAmountVoucher.getVoucherId());

        // Then
        assertThat(order.totalAmount(), is(100L));
        assertThat(order.getVoucher().isEmpty(), is(false));
        assertThat(order.getVoucher().get().getVoucherId(), is(fixedAmountVoucher.getVoucherId()));
        assertThat(order.getOrderStatus(), is(OrderStatus.ACCEPTED));
    }

    @Test
    @DisplayName("오더가 생성되야한다. (mock)")
    void createOrderByMock() {
        // Given
        var voucherServiceMock = mock(VoucherService.class);
        var orderRepositoryMock = mock(OrderRepository.class);
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100, LocalDateTime.now());
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
        var inOrder = inOrder(voucherServiceMock, orderRepositoryMock);
        inOrder.verify(voucherServiceMock).getVoucher(fixedAmountVoucher.getVoucherId());
        inOrder.verify(orderRepositoryMock).insert(order);
        inOrder.verify(voucherServiceMock).removeVoucherById(fixedAmountVoucher.getVoucherId());

    }


}
