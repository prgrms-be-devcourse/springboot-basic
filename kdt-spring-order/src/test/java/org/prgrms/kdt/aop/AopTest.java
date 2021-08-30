package org.prgrms.kdt.aop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.repository.voucher.MemoryVoucherRepository;
import org.prgrms.kdt.repository.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@ActiveProfiles("test")
public class AopTest {

    @Configuration
    @EnableAspectJAutoProxy
    @ComponentScan(includeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE,
            classes = {LoggingAspect.class}))
    static class TestConfig {

        @Bean
        VoucherRepository voucherRepository() {
            return new MemoryVoucherRepository();
        }
    }

    @Autowired
    ApplicationContext applicationContext;

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
    @DisplayName("Aop test")
    public void testOrderService() throws Exception {
        // given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100, LocalDateTime.now());
        voucherRepository.insert(fixedAmountVoucher);

    }
}
