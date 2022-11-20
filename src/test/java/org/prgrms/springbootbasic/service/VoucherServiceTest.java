package org.prgrms.springbootbasic.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.springbootbasic.entity.voucher.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.voucher.PercentAmountVoucher;
import org.prgrms.springbootbasic.entity.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.prgrms.springbootbasic.type.VoucherType.FIXED;
import static org.prgrms.springbootbasic.type.VoucherType.PERCENT;

@SpringJUnitConfig
@ActiveProfiles("default")
@ExtendWith(MockitoExtension.class)
@ContextConfiguration
class VoucherServiceTest {

    @Configuration
    @ComponentScan(basePackages = {
            "org.prgrms.springbootbasic.repository",
            "org.prgrms.springbootbasic.service",
            "org.prgrms.springbootbasic.configuration"
    })
    static class AppConfig {
    }

    @Autowired
    ApplicationContext context;

    @Autowired
    VoucherService voucherService;


    @Test
    @DisplayName("FixedVoucher 생성 - 성공")
    public void testCreateFixedAmountVoucher_success() {
        Voucher savedVoucher = voucherService.createVoucher(FIXED, 100L);
        assertThat(savedVoucher.getClass(), is(FixedAmountVoucher.class));
        assertThat(savedVoucher.getQuantity(), is(100L));

    }

    @Test
    @DisplayName("FixedVoucher 생성 - 실패 : 0 미만")
    public void testCreateFixedAmountVoucher_fail_under_0() {
        assertThrows(IllegalArgumentException.class, () -> voucherService.createVoucher(FIXED, -10L));
    }

    @Test
    @DisplayName("PercentVoucher 생성 - 성공")
    public void testCreatePercentAmountVoucher_success() {
        Voucher savedVoucher = voucherService.createVoucher(PERCENT, 10L);
        assertThat(savedVoucher.getClass(), is(PercentAmountVoucher.class));
        assertThat(savedVoucher.getQuantity(), is(10L));

    }

    @Test
    @DisplayName("PercentVoucher 생성 - 실패 : 100 초과")
    public void testCreatePercentAmountVoucher_fail_exceed_100() {
        assertThrows(IllegalArgumentException.class, () -> voucherService.createVoucher(PERCENT, 101L));
    }

    @Test
    @DisplayName("PercentVoucher 생성 - 실패 : 0 미만")
    public void testCreatePercentAmountVoucher_fail_under_0() {
        assertThrows(IllegalArgumentException.class, () -> voucherService.createVoucher(PERCENT, -10L));
    }


}