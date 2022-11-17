package com.programmers.VoucherManagementApplication.service;

import com.programmers.VoucherManagementApplication.repository.MemoryVoucherRepository;
import com.programmers.VoucherManagementApplication.repository.VoucherRepository;
import com.programmers.VoucherManagementApplication.vo.Amount;
import com.programmers.VoucherManagementApplication.vo.VoucherType;
import com.programmers.VoucherManagementApplication.voucher.FixedVoucher;
import com.programmers.VoucherManagementApplication.voucher.PercentVoucher;
import com.programmers.VoucherManagementApplication.voucher.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class VoucherServiceTest {

    VoucherRepository voucherRepository = new MemoryVoucherRepository();

    @Test
    @DisplayName("VoucherService create 성공")
    void create_success() {
        // given
        Voucher voucher = new FixedVoucher(UUID.randomUUID(), VoucherType.FIXED_DISCOUNT, new Amount(100L));

        // when
        Voucher sut = new VoucherService(voucherRepository).create(voucher.getVoucherType(), voucher.getAmount());
        UUID findVoucherId = voucherRepository.findById(sut.getVoucherId()).get().getVoucherId();

        // then
        assertEquals(sut.getVoucherId(), findVoucherId);
    }

    @Test
    @DisplayName("VoucherService - FixedVoucher create 실패")
    void fixedVoucher_create_fail() {
        assertThrows(IllegalArgumentException.class, () -> new FixedVoucher(UUID.randomUUID(), VoucherType.FIXED_DISCOUNT, new Amount(100000L)));
    }

    @Test
    @DisplayName("VoucherService - PercentVoucher create 실패")
    void percentVoucher_create_fail() {
        assertThrows(IllegalArgumentException.class, () -> new PercentVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new Amount(200L)));
    }

    @Test
    @DisplayName("VoucherService - findAll 성공")
    void findAll() {
        // given
        Voucher voucher1 = new FixedVoucher(UUID.randomUUID(), VoucherType.FIXED_DISCOUNT, new Amount(100L));
        Voucher voucher2 = new FixedVoucher(UUID.randomUUID(), VoucherType.FIXED_DISCOUNT, new Amount(10L));
        Voucher voucher3 = new PercentVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new Amount(50L));
        voucherRepository.addVoucher(voucher1);
        voucherRepository.addVoucher(voucher2);
        voucherRepository.addVoucher(voucher3);

        // when
        Map<UUID, Voucher> all = new VoucherService(voucherRepository).findAll();

        // then
        assertThat(all.size(), is(3));
    }
}