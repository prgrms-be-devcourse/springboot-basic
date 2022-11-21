package org.prgrms.voucherapplication.aop;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.voucherapplication.voucher.entity.FixedAmountVoucher;
import org.prgrms.voucherapplication.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.UUID;

@SpringJUnitConfig
@ActiveProfiles("prod")
class AopTest {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.voucherapplication.aop", "org.prgrms.voucherapplication.voucher", "org.prgrms.voucherapplication.customer", "org.prgrms.voucherapplication.console"}
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
    void testAop() {
        OutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100, LocalDateTime.now());
        voucherRepository.save(fixedAmountVoucher);

        MatcherAssert.assertThat(out.toString(), Matchers.containsString("LoggingAspect - Before method called."));
        MatcherAssert.assertThat(out.toString(), Matchers.containsString("LoggingAspect - After method called with result"));
    }
}
