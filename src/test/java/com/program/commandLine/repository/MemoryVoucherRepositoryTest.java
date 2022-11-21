package com.program.commandLine.repository;

import com.program.commandLine.voucher.VoucherFactory;
import com.program.commandLine.voucher.FixedAmountVoucher;
import com.program.commandLine.voucher.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MemoryVoucherRepositoryTest {

    MemoryVoucherRepository memoryVoucherRepository = new MemoryVoucherRepository();

    @DisplayName("올바른 id 조회시 voucher를 리턴한다.")
    @Test
    void testFindByCorrectId() {
        // Given
        var voucherId = UUID.randomUUID();
        var newVoucher = new FixedAmountVoucher(voucherId, 4200);
        memoryVoucherRepository.insert(newVoucher);
        // When
        var findVoucher = memoryVoucherRepository.findById(voucherId);
        // Then
        assertThat(findVoucher.isPresent(), is(true));
        assertThat(findVoucher.get(), is(newVoucher));
    }

    @DisplayName("잘못된 id 조회시 빈 optional을 리턴한다. ")
    @Test
    void testFindByWrongId() {
        // Given
        var voucherId = UUID.randomUUID();

        // When
        var findVoucher = memoryVoucherRepository.findById(voucherId);
        // Then
        assertThat(findVoucher.isEmpty(), is(true));
    }

    @DisplayName("바우처를 정상 생성한다.")
    @Test
    void testInsertVoucher() {
        // Given
        var voucherId = UUID.randomUUID();
        var discount = 30;
        var voucherType = VoucherType.PERCENT_DISCOUNT;
        // When
        var newVoucher = memoryVoucherRepository.insert(new VoucherFactory().createVoucher(voucherType, voucherId, discount));
        // Then
        assertThat(newVoucher, notNullValue());
        assertThat(newVoucher.getVoucherId(), is(voucherId));
        assertThat(newVoucher.getVoucherType(), is(voucherType));
        assertThat(newVoucher.getVoucherDiscount(), is(discount));
    }

    @DisplayName("전체 바우처를 조회할 수 있다.")
    @Test
    void testFindAll() {
        // Given
        var newVoucher = new FixedAmountVoucher(UUID.randomUUID(), 3000);
        memoryVoucherRepository.insert(newVoucher);
        // When
        var vouchers = memoryVoucherRepository.findAll();
        // Then
        assertThat(vouchers, notNullValue());
        assertThat(vouchers.size() == 1, is(true));
        assertThat(vouchers, hasItem(newVoucher));
    }
}