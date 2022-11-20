package com.programmers.VoucherManagementApplication.repository;

import com.programmers.VoucherManagementApplication.vo.Amount;
import com.programmers.VoucherManagementApplication.vo.VoucherType;
import com.programmers.VoucherManagementApplication.voucher.FixedVoucher;
import com.programmers.VoucherManagementApplication.voucher.PercentVoucher;
import com.programmers.VoucherManagementApplication.voucher.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("memory")
class MemoryVoucherRepositoryTest {

    VoucherRepository voucherRepository = new MemoryVoucherRepository();

    @Test
    @DisplayName("바우처 저장 성공")
    void addVoucher() {
        // given, voucher null값인 경우
        Voucher voucher = new FixedVoucher(UUID.randomUUID(), VoucherType.FIXED_DISCOUNT, new Amount(100L));

        // when
        Voucher addVoucher = voucherRepository.insert(voucher);

        // then
        assertEquals(addVoucher.getVoucherId(), voucher.getVoucherId());
    }

    @Test
    @DisplayName("바우처 전체 조회 성공")
    void findAll() {
        // given
        Voucher fixedVoucher = new FixedVoucher(UUID.randomUUID(), VoucherType.FIXED_DISCOUNT, new Amount(100L));
        Voucher percentVoucher = new FixedVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new Amount(10L));
        voucherRepository.insert(fixedVoucher);
        voucherRepository.insert(percentVoucher);

        // when
        var vouchers = voucherRepository.findAll();

        // then
        assertThat(vouchers.size(), is(2));
    }

    @Test
    @DisplayName("바우처 아이디로 조회 성공")
    void findById() {
        // given
        Voucher voucher = new PercentVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new Amount(50L));
        voucherRepository.insert(voucher);

        // when
        Optional<Voucher> findVoucher = voucherRepository.findById(voucher.getVoucherId());

        // then
        assertThat(findVoucher.isEmpty(), is(false));
        assertNotNull(findVoucher);
        assertThat(findVoucher.get().getVoucherId(), is(voucher.getVoucherId()));
    }

    @Test
    @DisplayName("저장되어있지 않은 바우처 아이디로 조회")
    void findById_fail() {
        // given
        Voucher voucher = new PercentVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new Amount(50L));
        voucherRepository.insert(voucher);

        // when
        Optional<Voucher> findVoucher = voucherRepository.findById(UUID.randomUUID());

        // then
        assertThat(findVoucher.isEmpty(), is(true));
    }

    @Test
    @DisplayName("저장된 바우처가 없을 때 아이디로 조회")
    void findById_fail_All() {
        Optional<Voucher> findVoucher = voucherRepository.findById(UUID.randomUUID());
        assertThat(findVoucher.isEmpty(), is(true));
    }
}