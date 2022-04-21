package org.prgms.voucher.service;

import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.EmbeddedTestDbInitializer;
import org.prgms.TestDbConfig;
import org.prgms.voucher.FixedAmountVoucher;
import org.prgms.voucher.PercentDiscountVoucher;
import org.prgms.voucher.Voucher;
import org.prgms.voucher.VoucherConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.UUID;

@SpringJUnitConfig(value = {TestDbConfig.class, VoucherConfig.class}, initializers = EmbeddedTestDbInitializer.class)
class VoucherServiceTest {

    @Autowired
    private VoucherService voucherService;

    @AfterEach
    void deleteAll() {
        voucherService.deleteAllVouchers();
    }

    public final Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 500L);
    public final Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10L);

    @Test
    @DisplayName("바우처 만들기 테스트")
    void createTest() {
        voucherService.createVoucher(fixedAmountVoucher);
        MatcherAssert.assertThat(voucherService.listVoucher(), Matchers.hasSize(1));
    }

    @Test
    @DisplayName("바우처 조회 테스트")
    void selectTest() {
        voucherService.createVoucher(fixedAmountVoucher);
        voucherService.createVoucher(percentDiscountVoucher);
        MatcherAssert.assertThat(voucherService.listVoucher(), Matchers.hasSize(2));
    }

    @Test
    @DisplayName("바우처 적용 테스트")
    void voucherUseTest() {
        voucherService.createVoucher(fixedAmountVoucher);
        voucherService.createVoucher(percentDiscountVoucher);
        Assertions.assertThat(voucherService.useVoucher(
                        1000L, fixedAmountVoucher.getVoucherId()))
                .isEqualTo(500L);
        Assertions.assertThat(voucherService.useVoucher(
                        1000L, percentDiscountVoucher.getVoucherId()))
                .isEqualTo(900L);
    }
}