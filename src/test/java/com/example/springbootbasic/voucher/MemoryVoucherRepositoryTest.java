package com.example.springbootbasic.voucher;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ActiveProfiles("test")
class MemoryVoucherRepositoryTest {

    @BeforeAll
    static void setup() {
        // DB 전환을 위한 setup 메소드
    }

    private static final Voucher fixedAmountVoucher = new FixedAmountVoucher(1000);
    private static final Voucher percentDiscountVoucher = new PercentDiscountVoucher(10);

    MemoryVoucherRepository memoryVoucherRepository = new MemoryVoucherRepository();
    @Test
    @DisplayName("바우처를 추가할 수 있다")
    void testSaveVoucher() {
        memoryVoucherRepository.save(fixedAmountVoucher);
        memoryVoucherRepository.save(percentDiscountVoucher);
        var vouchers = memoryVoucherRepository.findAll();
        assertThat(vouchers.isEmpty(), is(false));
    }


}