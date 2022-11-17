package org.prgrms.kdt.storage;

import org.junit.jupiter.api.*;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MemoryVoucherStorageTest {

    private VoucherStorage voucherStorage = new MemoryVoucherStorage();

    @Test
    @DisplayName("바우처를 저장할 수 있다.")
    void testSave() {
        // given
        Voucher fixedAmountVoucher = createFixedAmountVoucher();
        Voucher percentAmountVoucher = createPercentDiscountVoucher();
        // when
        voucherStorage.save(fixedAmountVoucher);
        voucherStorage.save(percentAmountVoucher);
        // then
        assertEquals(fixedAmountVoucher,
                voucherStorage.findById(
                        fixedAmountVoucher.getVoucherId())
                        .get());
        assertEquals(percentAmountVoucher,
                voucherStorage.findById(
                        percentAmountVoucher.getVoucherId())
                        .get());
    }

    @Test
    @DisplayName("생성된 바우처가 없으면 빈 리스트가 반환된다.")
    void testFindNoVouchers() {
        List<Voucher> vouchers = voucherStorage.findAll();
        assertEquals(0, vouchers.size());
    }

    @Test
    @DisplayName("저장된 바우처를 모두 불러올 수 있다.")
    void testFindAllVouchers(){
        // given
        voucherStorage.save(createFixedAmountVoucher());
        voucherStorage.save(createPercentDiscountVoucher());
        // when
        List<Voucher> vouchers = voucherStorage.findAll();
        // then
        assertEquals(2, vouchers.size());
    }

    private Voucher createFixedAmountVoucher() {
        return new FixedAmountVoucher(UUID.randomUUID(), 1000);
    }

    private Voucher createPercentDiscountVoucher() {
        return new PercentDiscountVoucher(UUID.randomUUID(), 50);
    }
}