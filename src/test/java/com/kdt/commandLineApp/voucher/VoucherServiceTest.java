package com.kdt.commandLineApp.voucher;

import com.kdt.commandLineApp.AppContext;
import com.kdt.commandLineApp.service.voucher.VoucherRepository;
import com.kdt.commandLineApp.service.voucher.VoucherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringJUnitConfig(classes = {AppContext.class})
@ActiveProfiles("db")
class VoucherServiceTest {
    @Autowired
    VoucherService voucherService;

    @Autowired
    VoucherRepository voucherRepository;

    @Test
    void voucherService() {
        try {
            voucherRepository.deleteAll();
            voucherService.addVoucher("percent",100);
            int result = voucherService.getVouchers(0,10,null).size();
            assertThat(result, is(1));

            voucherService.addVoucher("fixed",100);
            result = voucherService.getVouchers(0,10,null).size();
            assertThat(result, is(2));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}