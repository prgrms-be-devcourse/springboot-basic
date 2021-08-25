package com.programmers.kdtspringorder.aop;


import com.programmers.kdtspringorder.AppConfiguration;
import com.programmers.kdtspringorder.order.*;
import com.programmers.kdtspringorder.voucher.VoucherService;
import com.programmers.kdtspringorder.voucher.domain.FixedAmountVoucher;
import com.programmers.kdtspringorder.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@SpringJUnitConfig
@ActiveProfiles("test")
public class LoggingAspectTest {

    @Configuration
    @ComponentScan(
            basePackages = {"com.programmers.kdtspringorder.voucher", "com.programmers.kdtspringorder.aop"}
    )
    @EnableAspectJAutoProxy
    static class Config{}

    @Autowired
    ApplicationContext context;

    @Autowired
    VoucherRepository voucherRepository;

    @Test
    @DisplayName("AOP Logging 테스트")
    public void aopLoggingTest() throws Exception{
        // Given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100L);
        voucherRepository.save(fixedAmountVoucher);
    }

    @Test
    @DisplayName("AOP timeTrack 테스트")
    public void aopTest() throws Exception{
        // Given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100L);
        voucherRepository.save(fixedAmountVoucher);
    }

}
