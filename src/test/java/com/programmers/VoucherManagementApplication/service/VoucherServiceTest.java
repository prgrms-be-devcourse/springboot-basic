package com.programmers.VoucherManagementApplication.service;

import com.programmers.VoucherManagementApplication.repository.MemoryVoucherRepository;
import com.programmers.VoucherManagementApplication.vo.Amount;
import com.programmers.VoucherManagementApplication.vo.VoucherType;
import com.programmers.VoucherManagementApplication.voucher.FixedVoucher;
import com.programmers.VoucherManagementApplication.voucher.PercentVoucher;
import com.programmers.VoucherManagementApplication.voucher.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VoucherServiceTest {

    VoucherService voucherService = new VoucherService(new MemoryVoucherRepository());

    @Test
    @DisplayName("VoucherService create 성공")
    void create_success() {
        // given
        int beforeSize = voucherService.findAll().size();

        // when
        Voucher sut = voucherService.create(VoucherType.PERCENT_DISCOUNT, new Amount(100L));
        int afterSize = voucherService.findAll().size();

        // then
        assertEquals(afterSize, beforeSize + 1);
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

        voucherService.create(VoucherType.FIXED_DISCOUNT, new Amount(100L));
        voucherService.create(VoucherType.FIXED_DISCOUNT, new Amount(10L));
        voucherService.create(VoucherType.FIXED_DISCOUNT, new Amount(50L));


        // when
        List<Voucher> all = voucherService.findAll();

        // then
        assertThat(all.size(), is(3));
    }
}