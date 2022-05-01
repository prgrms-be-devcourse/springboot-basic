package org.prgrms.springbootbasic.aop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbootbasic.repository.voucher.MemoryVoucherRepository;
import org.prgrms.springbootbasic.repository.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@ActiveProfiles("local")
class LoggingAspectTest {

    @Autowired
    VoucherRepository voucherRepository;

    @DisplayName("logging aop 테스트")
    @Test
    void testLogging() {
        voucherRepository.removeAll();
    }

    @Configuration
    @ComponentScan(
        basePackages = {"org.prgrms.springbootbasic.aop"}
    )
    @EnableAspectJAutoProxy
    static class Config {

        @Bean
        public VoucherRepository voucherRepository() {
            return new MemoryVoucherRepository();
        }
    }

}