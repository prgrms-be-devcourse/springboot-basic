package com.programmers.vouchermanagement.voucher.infrastructure;

import com.programmers.vouchermanagement.voucher.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryVoucherRepositoryTest {

    MemoryVoucherRepository memoryVoucherRepository = new MemoryVoucherRepository();

    @Test
    @DisplayName("바우처를 저장한다.")
    void save() {
        // given
        DiscountType discountType = DiscountType.FIX;
        Voucher voucher = discountType.createVoucher(100);

        // when
        Voucher result = memoryVoucherRepository.save(voucher);

        // then
        assertThat(result.getId()).isEqualTo(voucher.getId());
    }

    @Test
    @DisplayName("바우처를 모두 조회한다.")
    void findAll() {
        // given
        DiscountPolicy discountPolicy1 = new FixedAmountDiscountPolicy(5000);
        DiscountPolicy discountPolicy2 = new PercentDiscountPolicy(10);
        Voucher voucher1 = new Voucher(discountPolicy1);
        Voucher voucher2 = new Voucher(discountPolicy2);

        memoryVoucherRepository.save(voucher1);
        memoryVoucherRepository.save(voucher2);

        // when
        List<Voucher> result = memoryVoucherRepository.findAll();

        // then
        assertThat(result).hasSize(2);
    }
}