package org.prgms.voucher.service;

import org.junit.jupiter.api.AfterEach;
import org.prgms.voucher.domain.FixedAmountVoucher;
import org.prgms.voucher.domain.PercentDiscountVoucher;
import org.prgms.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
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

}