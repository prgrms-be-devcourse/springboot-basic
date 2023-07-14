package com.programmers.voucher.domain.voucher.repository;

import com.programmers.voucher.domain.voucher.entity.Voucher;
import com.programmers.voucher.domain.voucher.entity.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class JdbcVoucherRepositoryTest {
    @Autowired
    private JdbcVoucherRepository voucherRepository;

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

    @Test
    @DisplayName("바우처 조회에 성공한다.")
    void 바우처_조회() {
        // given
        Voucher voucher = Voucher.builder().id(UUID.randomUUID()).type(VoucherType.FIXED).amount(100).build();
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
        Voucher voucher = Voucher.builder().id(UUID.randomUUID()).type(VoucherType.FIXED).amount(100).build();
        voucherRepository.insert(voucher);

        // when
        Voucher expected = Voucher.builder().id(voucher.getId()).type(VoucherType.PERCENT).amount(20).build();
        voucherRepository.update(expected);

        // then
        Voucher result = voucherRepository.findById(voucher.getId()).orElseThrow();
        assertThat(result.getType()).isEqualTo(expected.getType());
        assertThat(result.getAmount()).isEqualTo(expected.getAmount());
    }

    @Test
    @DisplayName("바우처 삭제에 성공한다.")
    void 바우처_삭제() {
        // given
        Voucher voucher = Voucher.builder().id(UUID.randomUUID()).type(VoucherType.FIXED).amount(100).build();
        voucherRepository.insert(voucher);

        // when
        voucherRepository.delete(voucher.getId());

        // then
        List<Voucher> result = voucherRepository.findAll();
        assertThat(result).isEmpty();
    }
}
