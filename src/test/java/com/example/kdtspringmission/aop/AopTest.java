package com.example.kdtspringmission.aop;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.kdtspringmission.voucher.domain.FixedAmountVoucher;
import com.example.kdtspringmission.voucher.repository.VoucherRepository;
import java.util.UUID;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

// @ExtendWith(SpringExtension.class)
// @ContextConfiguration  // 별도의 설정정보를 넘겨주지 않으면 내부에 설정 정보가 있는지 찾아봄
@SpringJUnitConfig
@EnableAspectJAutoProxy
@Disabled
class AopTest {

    @Configuration
    @ComponentScan(
        basePackages = {"com.example.kdtspringmission.voucher", "com.example.kdtspringmission.customer.aop"}
    )
    static class Config {
    }

    @Autowired
    ApplicationContext ac;

    @Autowired
    VoucherRepository voucherRepository;

    @Test
    void aopTest() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100L);
        voucherRepository.insert(fixedAmountVoucher);
    }
}
