package org.prgrms.kdt.storage;

import org.junit.jupiter.api.*;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemoryVoucherStorageTest {

    private final VoucherStorage voucherStorage = new MemoryVoucherStorage();

    @Test
    @DisplayName("두 가지 종류의 바우처를 저장할 수 있다.")
    void testSave() {
        // given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID().toString(), 1000);
        Voucher percentAmountVoucher = new PercentDiscountVoucher(UUID.randomUUID().toString(), 50);

        // when
        voucherStorage.save(fixedAmountVoucher);
        voucherStorage.save(percentAmountVoucher);

        // then
        voucherStorage.findById(fixedAmountVoucher.getVoucherId())
                .ifPresent(findVoucher ->
                        assertThat(findVoucher).usingRecursiveComparison()
                                .isEqualTo(fixedAmountVoucher));

        voucherStorage.findById(percentAmountVoucher.getVoucherId())
                .ifPresent(findVoucher ->
                        assertThat(findVoucher).usingRecursiveComparison()
                                .isEqualTo(percentAmountVoucher));
    }

    @Test
    @DisplayName("생성된 바우처가 없으면 빈 리스트가 반환된다.")
    void testFindNoVouchers() {
        List<Voucher> vouchers = voucherStorage.findAll();

        assertEquals(0, vouchers.size());
    }

    @Test
    @DisplayName("저장된 바우처를 모두 불러올 수 있다.")
    void testFindAllVouchers() {
        // given
        voucherStorage.save(new FixedAmountVoucher(UUID.randomUUID().toString(), 1000));
        voucherStorage.save(new PercentDiscountVoucher(UUID.randomUUID().toString(), 50));

        // when
        List<Voucher> vouchers = voucherStorage.findAll();

        // then
        assertEquals(2, vouchers.size());
    }

    @Test
    @DisplayName("바우처 Id를 이용하여 특정 바우처를 삭제할 수 있다.")
    void testDelete() {
        // given
        String id = UUID.randomUUID().toString();
        Voucher voucher = new FixedAmountVoucher(id, 1000);
        voucherStorage.save(voucher);

        // when
        voucherStorage.deleteById(id);

        // then
        assertFalse(voucherStorage.findAll().contains(voucher));
    }
}