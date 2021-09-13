package org.prgrms.kdtspringdemo.voucher.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdtspringdemo.VoucherType;
import org.prgrms.kdtspringdemo.common.Config;
import org.prgrms.kdtspringdemo.voucher.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.PercentDiscountVoucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@SpringJUnitConfig
@ContextConfiguration(classes = Config.class)
class VoucherServiceTest {
    @Autowired
    VoucherService voucherService;
    @Test
    @DisplayName("Voucher 생성하기")
    void createFixedAmountVoucher() {
        var fixVoucher = voucherService.saveVoucher("F", "10");
        var percentVoucher = voucherService.saveVoucher("P", "20");

        assertThat(fixVoucher.getDiscount(), is(10L));
        assertThat(voucherService.getVoucher(fixVoucher.getVoucherId()), is(fixVoucher));
        assertThat(voucherService.getVoucher(percentVoucher.getVoucherId()), is(percentVoucher));

    }
}