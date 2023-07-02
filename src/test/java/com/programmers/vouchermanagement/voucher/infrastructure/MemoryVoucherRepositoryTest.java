package com.programmers.vouchermanagement.voucher.infrastructure;

import com.programmers.vouchermanagement.voucher.domain.FixedAmountVoucher;
import com.programmers.vouchermanagement.voucher.domain.PercentDiscountVoucher;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
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
        FixedAmountVoucher voucher = new FixedAmountVoucher(100);

        // when
        Voucher result = memoryVoucherRepository.save(voucher);

        // then
        assertThat(result.getId()).isEqualTo(voucher.getId());
    }

    @Test
    @DisplayName("바우처를 모두 조회한다.")
    void findAll() {
        // given
        FixedAmountVoucher voucher1 = new FixedAmountVoucher(100);
        PercentDiscountVoucher voucher2 = new PercentDiscountVoucher(30);

        memoryVoucherRepository.save(voucher1);
        memoryVoucherRepository.save(voucher2);

        // when
        List<Voucher> result = memoryVoucherRepository.findAll();

        // then
        assertThat(result).hasSize(2);
    }
}