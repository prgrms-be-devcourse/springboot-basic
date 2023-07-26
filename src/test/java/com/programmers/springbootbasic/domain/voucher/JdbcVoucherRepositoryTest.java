package com.programmers.springbootbasic.domain.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(JdbcVoucherRepository.class)
class JdbcVoucherRepositoryTest {
    @Autowired
    private JdbcVoucherRepository repository;

    @ParameterizedTest
    @EnumSource(VoucherType.class)
    @DisplayName("바우처를 저장할 수 있다")
    void save(VoucherType voucherType) {
        // given
        Voucher voucher = switch (voucherType) {
            case FIX -> Voucher.createFixedAmount(UUID.randomUUID(), voucherType, 10000);
            case PERCENT -> Voucher.createPercentDiscount(UUID.randomUUID(), voucherType, 10);
        };

        // when
        Optional<Voucher> save = repository.save(voucher);
        // then
        assertThat(save).isPresent();
    }

    @Test
    @DisplayName("바우처를 모두 조회할 수 있다")
    void findAll() {
        // given
        Voucher fix = Voucher.createFixedAmount(UUID.randomUUID(), VoucherType.FIX, 10000);
        Voucher percent = Voucher.createPercentDiscount(UUID.randomUUID(), VoucherType.PERCENT, 10);
        repository.save(fix);
        repository.save(percent);
        // when
        List<Voucher> all = repository.findAll();
        // then
        assertThat(all).hasSize(2);
    }
}
