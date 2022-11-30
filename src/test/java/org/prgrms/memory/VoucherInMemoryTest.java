package org.prgrms.memory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.voucher.discountType.DiscountAmount;
import org.prgrms.voucher.discountType.DiscountRate;
import org.prgrms.voucher.voucherType.FixedAmountVoucher;
import org.prgrms.voucher.voucherType.PercentDiscountVoucher;
import org.prgrms.voucher.voucherType.Voucher;

class VoucherInMemoryTest {

    private VoucherInMemory voucherMemory;

    @BeforeEach
    void setUp() {
        voucherMemory = new VoucherInMemory();
    }

    @DisplayName("바우처(Fixed)를 저장하고 저장한 값을 리턴한다")
    @Test
    void saveFixedVoucher() {
        //given
        Voucher voucherBeforeSaved = new FixedAmountVoucher(UUID.randomUUID(),
            new DiscountAmount(100L),
            LocalDateTime.now());
        //when
        Voucher savedVoucher = voucherMemory.save(voucherBeforeSaved);
        //then
        assertEquals(voucherBeforeSaved, savedVoucher);
    }

    @DisplayName("바우처(Percent)를 저장하고 저장한 값을 리턴한다")
    @Test
    void savePercentVoucher() {
        //given
        Voucher voucherBeforeSaved = new PercentDiscountVoucher(UUID.randomUUID(),
            new DiscountRate(100L), LocalDateTime.now());
        //when
        Voucher savedVoucher = voucherMemory.save(voucherBeforeSaved);
        //then
        assertEquals(voucherBeforeSaved, savedVoucher);
    }


    @DisplayName("저장된 모든 바우처를 반환한다.")
    @Test
    void findAll() {
        //given
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), new DiscountAmount(10L),
            LocalDateTime.now());
        Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), new DiscountAmount(10L),
            LocalDateTime.now());
        voucherMemory.save(voucher1);
        voucherMemory.save(voucher2);
        //when
        List<Voucher> vouchers = voucherMemory.findAll();
        //then
        assertEquals(vouchers.size(), 2);

    }

    @DisplayName("저장된 바우처가 없다면 빈 배열을 리턴한다")
    @Test
    void withoutVoucher() {
        //when
        List<Voucher> vouchers = voucherMemory.findAll();
        //then
        assertTrue(vouchers.isEmpty());
    }
}
