package com.programmers.voucher.repository;

import com.programmers.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.Voucher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryVoucherRepositoryTest {
    MemoryVoucherRepository repository = new MemoryVoucherRepository();

    @AfterEach
    void afterEach() {
        repository.clear();
    }

    @Test
    @DisplayName("바우처 저장에 성공한다.")
    void 바우처_저장() {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 3000L);
        // when
        repository.insert(voucher);
        // then
        Voucher result = repository.findById(voucher.getVoucherId()).orElse(null);
        assertThat(result).isEqualTo(voucher);
    }

    @Test
    @DisplayName("바우처 리스트 조회에 성공한다.")
    void 바우처_리스트_조회() {
        // given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 3000L);
        repository.insert(fixedAmountVoucher);

        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 20L);
        repository.insert(percentDiscountVoucher);
        // when
        List<Voucher> result = repository.findAll();
        // then
        assertThat(result.size()).isEqualTo(2);
    }
}
