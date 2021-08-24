package org.prgrms.kdt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.prgrms.kdt.order.OrderItem;
import org.prgrms.kdt.order.OrderStatus;
import org.prgrms.kdt.order.service.OrderService;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.repository.MemoryVoucherRepository;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;


// Spring에서 앞의 기능들을 다 제공함 ㅋㅋㅋ
@SpringJUnitConfig // ()
@ActiveProfiles("test") // JDBC: "dev"
public class KdtSpringContextTest {

    // 실제 Application Context에서 끄집어 내서 테스트 할꺼야
    @Configuration
    @ComponentScan(basePackages = {
            "org.prgrms.kdt.order",
            "org.prgrms.kdt.voucher",
            "org.prgrms.kdt.command",
            "org.prgrms.kdt.user"
    })
    static class Config {
    }

    @Autowired
    ApplicationContext context;

    @Autowired
    OrderService orderService;
    @Autowired
    VoucherRepository voucherRepository;

   @Test
    @DisplayName("applicationContext가 생성되어야 한다.")
    public void testApplicationContext() {
        assertThat(context, notNullValue());
    }

    @Test
    @DisplayName("VoucherRepository가 빈으로 등록되어 있어야 한다.")
    public void testVoucherRepositoryCreation() {
        var bean = context.getBean(VoucherRepository.class);
        assertThat(bean, notNullValue());
    }

    // 실제 Bean들의 상호 협력 관계를 통합테스트 한거임.
    @Test
    @DisplayName("OrderService를 사용해서 주문을 생성할 수 있다.")
    public void testOrderService() {
//        Given : 상
        var fixedAmountVoucher  = new FixedAmountVoucher(UUID.randomUUID(), 100);
        voucherRepository.insert(fixedAmountVoucher);

//        WHEN : method
        var order = orderService.createOrder(UUID.randomUUID(), List.of(new OrderItem(UUID.randomUUID(), 200, 1)), fixedAmountVoucher.getVoucherId());

//        THEN : 검증 확인
        assertThat(order.totalAmount(), is(100L));

        assertThat(order.getVoucher().isEmpty(), is(false));
        assertThat(order.getVoucher().get().getVoucherId(), is(fixedAmountVoucher.getVoucherId()));

        assertThat(order.getOrderStatus(), is(OrderStatus.ACCEPTED));
    }
}
