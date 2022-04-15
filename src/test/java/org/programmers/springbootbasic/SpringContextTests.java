package org.programmers.springbootbasic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.voucher.repository.VoucherRepository;
import org.programmers.springbootbasic.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringJUnitConfig
@ActiveProfiles("test")
public class KdtSpringContextTests {

    @Configuration
    @ComponentScan(
            basePackages = {"org.programmers.springbootbasic.voucher", "org.programmers.springbootbasic.customer"}
    )
    static class Config {
    }

    @Autowired
    ApplicationContext context;

    @Autowired
    VoucherService voucherService;

    @Autowired
    VoucherRepository voucherRepository;

    @Test
    @DisplayName("applicationContext가 생성 되어야 한다.")
    public void testApplicationContext() {
        assertThat(context, notNullValue());
    }

    @Test
    @DisplayName("VoucherRepository가 생성 되어야 한다.")
    public void testVoucherRepositoryCreation() {
        var bean = context.getBean(VoucherRepository.class);
        assertThat(bean, notNullValue());
    }

    @Test
    @DisplayName("voucherService를 사용해서 바우처를 생성 할 수 있다.")
    public void testOrderService() {
        // Given
        long value = 10L;
        var voucherService = new VoucherService(voucherRepository);

        // When
        var voucher = voucherService.createVoucher(value, "FixedAmountVoucher");

        // Then
        assertThat(voucher.discount(100L), is(90L));
    }
}


