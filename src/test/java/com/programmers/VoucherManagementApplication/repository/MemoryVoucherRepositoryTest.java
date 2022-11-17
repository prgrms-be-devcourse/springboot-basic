package com.programmers.VoucherManagementApplication.repository;

import com.programmers.VoucherManagementApplication.vo.Amount;
import com.programmers.VoucherManagementApplication.vo.VoucherType;
import com.programmers.VoucherManagementApplication.voucher.FixedVoucher;
import com.programmers.VoucherManagementApplication.voucher.PercentVoucher;
import com.programmers.VoucherManagementApplication.voucher.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;


class MemoryVoucherRepositoryTest {

    VoucherRepository voucherRepository = new MemoryVoucherRepository();

    @Test
    @DisplayName("바우처 저장 성공")
    void addVoucher() {
        // given
        Voucher voucher = new FixedVoucher(UUID.randomUUID(), VoucherType.FIXED_DISCOUNT, new Amount(100L));

        // when
        Voucher addVoucher = voucherRepository.addVoucher(voucher);

        // then
        assertEquals(addVoucher.getVoucherId(), voucher.getVoucherId());
    }

    @Test
    @DisplayName("바우처 전체 조회 성공")
    void findAll() {
        // given
        Voucher fixedVoucher = new FixedVoucher(UUID.randomUUID(), VoucherType.FIXED_DISCOUNT, new Amount(100L));
        Voucher percentVoucher = new FixedVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new Amount(10L));
        voucherRepository.addVoucher(fixedVoucher);
        voucherRepository.addVoucher(percentVoucher);

        // when
        var voucherMap = voucherRepository.findAll();

        // then
        assertThat(voucherMap.size(), is(2));
        assertThat(voucherMap.values(), hasSize(2));
        assertThat(voucherMap.keySet(), hasItem(fixedVoucher.getVoucherId()));
        assertThat(voucherMap.keySet(), hasItem(percentVoucher.getVoucherId()));
    }

    @Test
    @DisplayName("바우처 아이디로 조회 성공")
    void findById() {
        // given
        Voucher voucher = new PercentVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new Amount(50L));
        voucherRepository.addVoucher(voucher);

        // when
        Optional<Voucher> findVoucher = voucherRepository.findById(voucher.getVoucherId());

        // then
        assertThat(findVoucher.isEmpty(), is(false));
        assertNotNull(findVoucher);
        assertThat(findVoucher.get().getVoucherId(), is(voucher.getVoucherId()));
    }
}