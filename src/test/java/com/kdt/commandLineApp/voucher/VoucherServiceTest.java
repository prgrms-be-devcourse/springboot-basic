package com.kdt.commandLineApp.voucher;

import com.kdt.commandLineApp.AppContext;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringJUnitConfig(classes = {AppContext.class})
@ActiveProfiles("dev")
class VoucherServiceTest {
    @Autowired
    ApplicationContext applicationContext;

    VoucherService voucherService;

    @Before
    void set() {
        voucherService = applicationContext.getBean("voucherService",VoucherService.class);
    }

    @Test
    void voucherService() {
        try {
            voucherService.addVoucher("percent",100f);
            assertThat(voucherService.getVouchers().size(), is(1));
            voucherService.addVoucher("percent",10f);
            assertThat(voucherService.getVouchers().size(), is(2));
        }
        catch (Exception e) {
        }
    }
}