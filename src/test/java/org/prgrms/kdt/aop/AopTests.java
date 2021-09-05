package org.prgrms.kdt.aop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.IOException;
import java.util.UUID;

@SpringJUnitConfig
@ActiveProfiles("test")
public class AopTests {

    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.kdt.voucher", "org.prgrms.kdt.aop"})
    @EnableAspectJAutoProxy
    static class Config {
    }

    @Autowired ApplicationContext context;
    @Autowired VoucherRepository voucherRepository;

    @Test
    @DisplayName("AOP test")
    public void testOrderService() throws IOException {
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        voucherRepository.create(fixedAmountVoucher);
    }
}
