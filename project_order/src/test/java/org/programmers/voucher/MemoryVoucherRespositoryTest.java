package org.programmers.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

class MemoryVoucherRespositoryTest {

    private final VoucherRepository memoryVoucherRespository = new MemoryVoucherRespository();

    @Test
    @DisplayName("특정 id를 가진 바우처를 불러온다")
    void findById() {
        // given
        UUID voucherId = UUID.randomUUID();
        Voucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, 10L);

        memoryVoucherRespository.insert(fixedAmountVoucher);

        // when
        Optional<Voucher> voucher = memoryVoucherRespository.findById(voucherId);

        // then
        assertEquals(Optional.of(fixedAmountVoucher), voucher);
    }

    @Test
    @DisplayName("새로운 바우처가 저장된다")
    void insert() {
        // given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);

        // when
        Voucher insertedVoucher = memoryVoucherRespository.insert(fixedAmountVoucher);

        // then
        assertThat(insertedVoucher, is(fixedAmountVoucher));
    }

    @Test
    @DisplayName("현재 메모리에 저장된 바우처를 모두 불러온다")
    void getAllVouchers() {
        // given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10L);

        memoryVoucherRespository.insert(fixedAmountVoucher);
        memoryVoucherRespository.insert(percentDiscountVoucher);

        // when
        List<Voucher> vouchers = memoryVoucherRespository.getAllVouchers();

        // then
        assertThat(vouchers, contains(fixedAmountVoucher, percentDiscountVoucher));
    }
}