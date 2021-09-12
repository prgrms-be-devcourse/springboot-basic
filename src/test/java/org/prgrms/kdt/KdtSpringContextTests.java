package org.prgrms.kdt;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.prgrms.kdt.order.OrderItem;
import org.prgrms.kdt.order.OrderService;
import org.prgrms.kdt.order.OrderStatus;
import org.prgrms.kdt.voucher.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

//Spring Text Context를 사용할 수 있음
@SpringJUnitConfig
@ActiveProfiles("test")
public class KdtSpringContextTests {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt.voucher", "org.prgrms.kdt.order"}
    )
    static class Config {

    }


    // ApplicationContext를 Autowired를 통해 가져올 수 있음
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    OrderService orderService;

    @Autowired
    VoucherRepository voucherRepository;

    @Test
    @DisplayName("applicationContext가 생성 되어야 한다.")
    public void testApplicationContxt() {
        assertThat(applicationContext, notNullValue());
    }


    @Test
    @DisplayName("VoucherRepository가 빈으로 등록되어 있어야 한다.")
    public void testVoucherRepositoryCreation() {
        var bean = applicationContext.getBean(VoucherRepository.class);
        assertThat(bean, notNullValue());
    }

    @Test
    @DisplayName("orderService를 사용해서 주문을 생성할 수 있다.")
    public void testorderService() {
        // Given
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        voucherRepository.insert(fixedAmountVoucher);


        // When
        var order = orderService.createOrder(
                UUID.randomUUID(),
                List.of(new OrderItem(UUID.randomUUID(), 200, 1)),
                fixedAmountVoucher.getVoucherID());

        // Then (검증 / Order의 상태에 집중)
        assertThat(order.totalAmount(), is(100L));
        assertThat(order.getVoucher().isEmpty(), is(false));
        assertThat(order.getVoucher().get().getVoucherID(), is(fixedAmountVoucher.getVoucherID()));
        assertThat(order.getOrderStatus(), is(OrderStatus.ACCEPTED));
    }
}
