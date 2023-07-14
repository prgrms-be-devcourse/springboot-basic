package com.programmers.voucher.domain.voucher.repository;

import com.programmers.voucher.domain.voucher.entity.Voucher;
import com.programmers.voucher.domain.voucher.entity.VoucherType;
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
        voucherRepository.clear();
    }

    @Test
    @DisplayName("바우처 생성에 성공한다.")
    void 바우처_생성() {
        // given
        Voucher voucher = Voucher.builder().id(UUID.randomUUID()).type(VoucherType.FIXED).amount(100).build();

        // when
        voucherRepository.insert(voucher);
        
        // then
        Voucher result = voucherRepository.findById(voucher.getId()).orElseThrow();
        assertThat(result.getId()).isEqualTo(voucher.getId());
    }

    @Test
    @DisplayName("모든 바우처 조회에 성공한다.")
    void 모든_바우처_조회() {
        // given
        Voucher voucher1 = Voucher.builder().id(UUID.randomUUID()).type(VoucherType.FIXED).amount(100).build();
        voucherRepository.insert(voucher1);
        Voucher voucher2 = Voucher.builder().id(UUID.randomUUID()).type(VoucherType.PERCENT).amount(20).build();
        voucherRepository.insert(voucher2);

        // when
        List<Voucher> result = voucherRepository.findAll();
        // then
        assertThat(result).hasSize(2);
    }
}
