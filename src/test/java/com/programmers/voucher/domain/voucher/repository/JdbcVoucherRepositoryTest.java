package com.programmers.voucher.domain.voucher.repository;

import com.programmers.voucher.domain.voucher.entity.Voucher;
import com.programmers.voucher.domain.voucher.entity.VoucherType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class JdbcVoucherRepositoryTest {
    @Autowired
    private JdbcVoucherRepository voucherRepository;

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

    @Test
    @DisplayName("바우처 조회에 성공한다.")
    void 바우처_조회() {
        // given
        Voucher voucher = new Voucher(UUID.randomUUID(), VoucherType.FIXED, 100);
        voucherRepository.insert(voucher);

        // when
        Voucher result = voucherRepository.findById(voucher.getId()).orElse(null);

        // then
        assertNotNull(result);
        assertThat(result.getId()).isEqualTo(voucher.getId());
    }

    @Test
    @DisplayName("바우처 수정에 성공한다.")
    void 바우처_수정() {
        // given
        Voucher voucher = new Voucher(UUID.randomUUID(), VoucherType.FIXED, 100);
        voucherRepository.insert(voucher);

        // when
        Voucher result = voucherRepository.update(new Voucher(voucher.getId(), VoucherType.PERCENT, 20));

        // then
        assertThat(result.getType()).isEqualTo(VoucherType.PERCENT);
        assertThat(result.getAmount()).isEqualTo(20);
    }

    @Test
    @DisplayName("바우처 삭제에 성공한다.")
    void 바우처_삭제() {
        // given
        Voucher voucher = new Voucher(UUID.randomUUID(), VoucherType.FIXED, 100);
        voucherRepository.insert(voucher);

        // when
        voucherRepository.delete(voucher.getId());

        // then
        List<Voucher> result = voucherRepository.findAll();
        assertThat(result).isEmpty();
    }
}
