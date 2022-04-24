package org.prgms.voucher.service;

import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.voucher.domain.FixedAmountVoucher;
import org.prgms.voucher.domain.PercentDiscountVoucher;
import org.prgms.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
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