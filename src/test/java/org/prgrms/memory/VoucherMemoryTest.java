package org.prgrms.memory;

import static org.junit.jupiter.api.Assertions.*;

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

class VoucherMemoryTest {

    private VoucherInMemory voucherMemory;

    @BeforeEach
    void setUp() {
        voucherMemory = new VoucherInMemory();
    }

    @DisplayName("save메소드는 save한 voucher를 리턴한다.")
    @Test
    void save() {
        //given
        Voucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), new DiscountAmount(1000L),
            LocalDateTime.now());
        Voucher percentVoucher = new PercentDiscountVoucher(UUID.randomUUID(),
            new DiscountRate(10L), LocalDateTime.now());
        //when
        Voucher savedFixed = voucherMemory.save(fixedVoucher);
        Voucher savedPercent = voucherMemory.save(percentVoucher);
        //then
        assertEquals(fixedVoucher, savedFixed);
        assertEquals(percentVoucher, savedPercent);
    }

    @DisplayName("findAll메소드는 저장된 바우처가 있으면 모든 바우처를 반환한다.")
    @Test
    void findAll() {
        //given
        final int VOUCHER_SIZE = 3;

        for (int i = 0; i < VOUCHER_SIZE; i++) {
            Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), new DiscountAmount(1000L),
                LocalDateTime.now());
            voucherMemory.save(voucher);
        }

        //when
        List<Voucher> vouchers = voucherMemory.findAll();
        //then
        assertEquals(VOUCHER_SIZE, vouchers.size());

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
