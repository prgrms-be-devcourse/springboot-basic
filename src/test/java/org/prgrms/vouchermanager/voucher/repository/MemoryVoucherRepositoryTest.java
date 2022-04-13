package org.prgrms.vouchermanager.voucher.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.voucher.domain.FixedAmountVoucher;
import org.prgrms.vouchermanager.voucher.domain.PercentDiscountVoucher;
import org.prgrms.vouchermanager.voucher.domain.Voucher;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MemoryVoucherRepositoryTest {

    @Test
    @DisplayName("voucherId로 Voucher를 찾을 수 있다.")
    void testWithFindById() {
        //given
        VoucherRepository voucherRepository = new MemoryVoucherRepository();
        Voucher fixedAmountVoucher = new FixedAmountVoucher(100L);
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(10);
        voucherRepository.insert(fixedAmountVoucher);
        voucherRepository.insert(percentDiscountVoucher);

        //when
        Voucher findFixedVoucher = voucherRepository.findById(fixedAmountVoucher.getVoucherId()).get();
        Voucher findPercentVoucher = voucherRepository.findById(percentDiscountVoucher.getVoucherId()).get();

        //then
        assertEquals(fixedAmountVoucher, findFixedVoucher);
        assertEquals(percentDiscountVoucher, findPercentVoucher);
    }
}