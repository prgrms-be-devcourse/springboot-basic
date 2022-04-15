package org.prgms.voucher.service;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.TestConfig;
import org.prgms.voucher.FixedAmountVoucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.UUID;

@SpringJUnitConfig(value = TestConfig.class)
class VoucherServiceTest {

    @Autowired
    private VoucherService voucherService;

    @AfterEach
    void deleteAll() {
        voucherService.deleteAllVouchers();
    }

    @Test
    @DisplayName("바우처 만들기 테스트")
    void createTest() {
        voucherService.createVoucher(new FixedAmountVoucher(10L, UUID.randomUUID()));
        MatcherAssert.assertThat(voucherService.listVoucher(), Matchers.hasSize(1));
    }

    @Test
    @DisplayName("바우처 조회 테스트")
    void selectTest() {
        voucherService.createVoucher(new FixedAmountVoucher(10L, UUID.randomUUID()));
        voucherService.createVoucher(new FixedAmountVoucher(20L, UUID.randomUUID()));
        MatcherAssert.assertThat(voucherService.listVoucher(), Matchers.hasSize(2));
    }
}