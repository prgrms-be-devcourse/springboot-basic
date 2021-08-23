package org.prgrms.kdt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.config.YamlPropertiesFactory;
import org.prgrms.kdt.domain.order.Order;
import org.prgrms.kdt.domain.order.OrderItem;
import org.prgrms.kdt.domain.order.OrderStatus;
import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.repository.voucher.VoucherRepository;
import org.prgrms.kdt.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@ActiveProfiles("test")
public class kdtSpringContextTest {

    @Configuration
    @ComponentScan(basePackageClasses = {CommandLineApplication.class})
    static class TestConfig {

    }


    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    OrderService orderService;

    @Autowired
    VoucherRepository voucherRepository;

    @Test
    @DisplayName("applicationContext가 실행되어야 한다")
    public void testApplicationContext() throws Exception {
        // given

        // when

        // then
        assertThat(applicationContext).isNotNull();
    }

    @Test
    @DisplayName("VoucherRepository가 빈으로 등록되어야 한다")
    public void testVoucherRepositoryBean() throws Exception {
        // given
        VoucherRepository voucherRepository = applicationContext.getBean(VoucherRepository.class);
        // when

        // then
        assertThat(voucherRepository).isNotNull();
    }

    @Test
    @DisplayName("OrderService를 사용해서 주문을 생성할 수 있다")
    public void testOrderService() throws Exception {
        // given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        voucherRepository.insert(fixedAmountVoucher);
        // when
        Order order = orderService.createOrder(
                UUID.randomUUID(),
                List.of(new OrderItem(UUID.randomUUID(), 200, 1)),
                fixedAmountVoucher.getVoucherId());

        // then
        assertThat(order.totalAmount()).isEqualTo(100L);
        assertThat(order.getVoucher()).isNotEmpty();
        assertThat(order.getVoucher().get().getVoucherId()).isEqualTo(fixedAmountVoucher.getVoucherId());
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ACCEPTED);

    }
}
