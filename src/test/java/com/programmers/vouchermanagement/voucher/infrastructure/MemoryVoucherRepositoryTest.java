package com.programmers.vouchermanagement.voucher.infrastructure;

import com.programmers.vouchermanagement.voucher.domain.*;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryVoucherRepositoryTest {

    MemoryVoucherRepository memoryVoucherRepository = new MemoryVoucherRepository();

    @AfterEach
    void afterEach() {
        memoryVoucherRepository.clearStorage();
    }

    @Test
    @DisplayName("바우처를 저장한다.")
    void save() {
        // given
        DiscountType discountType = DiscountType.FIX;
        Voucher voucher = discountType.createVoucher(100);

        // when
        memoryVoucherRepository.save(voucher);

        // then
        Voucher result = memoryVoucherRepository.findById(voucher.getId()).get();
        assertThat(result).isEqualTo(voucher);
    }

    @Test
    @DisplayName("바우처를 모두 조회한다.")
    void findAll() {
        // given
        Voucher voucher1 = new Voucher(new FixedAmountDiscountPolicy(5000), DiscountType.FIX);
        Voucher voucher2 = new Voucher(new PercentDiscountPolicy(10), DiscountType.PERCENT);

        memoryVoucherRepository.save(voucher1);
        memoryVoucherRepository.save(voucher2);

        // when
        List<Voucher> result = memoryVoucherRepository.findAll();

        // then
        assertThat(result).hasSize(2);
    }
}