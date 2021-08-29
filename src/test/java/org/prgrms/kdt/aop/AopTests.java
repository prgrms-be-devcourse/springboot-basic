package org.prgrms.kdt.aop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.order.OrderItem;
import org.prgrms.kdt.order.OrderService;
import org.prgrms.kdt.order.OrderStatus;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringJUnitConfig
@ActiveProfiles("test")
public class AopTests {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt.voucher", "org.prgrms.kdt.aop"}
    )

    @EnableAspectJAutoProxy
    static class Config {
    }

    @Autowired
    ApplicationContext context;

    @Autowired
    VoucherRepository voucherRepository;


    @Test
    @DisplayName("Aop test")
    public void testOrderService() {
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        voucherRepository.insert(fixedAmountVoucher);
    }
}
