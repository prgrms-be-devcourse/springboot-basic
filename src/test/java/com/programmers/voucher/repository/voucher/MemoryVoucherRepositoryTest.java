package com.programmers.voucher.repository.voucher;

import com.programmers.voucher.entity.voucher.Voucher;
import com.programmers.voucher.entity.voucher.VoucherType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryVoucherRepositoryTest {
    MemoryVoucherRepository voucherRepository = new MemoryVoucherRepository();

    @AfterEach
    void afterEach() {
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("바우처 생성에 성공한다.")
    void 바우처_생성() {
        // given
        Voucher voucher = new Voucher(UUID.randomUUID(), VoucherType.FIXED, 100);
        // when
        Voucher result = voucherRepository.insert(voucher);
        // then
        assertThat(result.getId()).isEqualTo(voucher.getId());
    }

    @Test
    @DisplayName("모든 바우처 조회에 성공한다.")
    void 모든_바우처_조회() {
        // given
        Voucher voucher1 = new Voucher(UUID.randomUUID(), VoucherType.FIXED, 100);
        voucherRepository.insert(voucher1);
        Voucher voucher2 = new Voucher(UUID.randomUUID(), VoucherType.PERCENT, 20);
        voucherRepository.insert(voucher2);

        // when
        List<Voucher> result = voucherRepository.findAll();
        // then
        assertThat(result).hasSize(2);
    }
}
