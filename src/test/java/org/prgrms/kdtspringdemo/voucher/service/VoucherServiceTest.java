package org.prgrms.kdtspringdemo.voucher.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdtspringdemo.VoucherType;
import org.prgrms.kdtspringdemo.common.Config;
import org.prgrms.kdtspringdemo.console.VoucherOperator;
import org.prgrms.kdtspringdemo.voucher.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.PercentDiscountVoucher;
import org.prgrms.kdtspringdemo.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@ContextConfiguration(classes = Config.class)
class VoucherServiceTest {
    @Autowired
    VoucherService voucherService;
    @Test
    @DisplayName("Voucher 생성하기")
    void createFixedAmountVoucher() {
        var fixVoucher = voucherService.saveVoucher(new FixedAmountVoucher(UUID.randomUUID(), "TestFixVoucher", 10L, VoucherType.FIXED_AMOUNT, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)));
        var percentVoucher = voucherService.saveVoucher(new PercentDiscountVoucher(UUID.randomUUID(), "TestFixVoucher",20L, VoucherType.PERCENT_DISCOUNT, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)));

        assertThat(fixVoucher.getAmount(), is(10L));
        assertThat(voucherService.getVoucher(fixVoucher.getVoucherId()), is(fixVoucher));
        assertThat(voucherService.getVoucher(percentVoucher.getVoucherId()), is(percentVoucher));

    }
}