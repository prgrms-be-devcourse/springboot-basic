package com.programmers.springbootbasic.domain.voucher;

import com.programmers.springbootbasic.domain.voucher.Repository.JdbcVoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(JdbcVoucherRepository.class)
class JdbcVoucherRepositoryTest {
    @Autowired
    private JdbcVoucherRepository repository;

    @ParameterizedTest
    @CsvSource(value = {"정률 할인, 10", "정액 할인, 10000"})
    @DisplayName("바우처를 저장할 수 있다")
    void save(String voucherType, int amountOrPercent) {
        // given
        Voucher voucher = Voucher.createVoucher(UUID.randomUUID(), VoucherType.from(voucherType), amountOrPercent);
        // when
        UUID id = repository.save(voucher);
        // then
        assertThat(id).isNotNull();
    }

    @Test
    @DisplayName("바우처를 모두 조회할 수 있다")
    void findAll() {
        // given
        Voucher fix = Voucher.createVoucher(UUID.randomUUID(), VoucherType.FIX, 10000);
        Voucher percent = Voucher.createVoucher(UUID.randomUUID(), VoucherType.PERCENT, 10);
        repository.save(fix);
        repository.save(percent);
        // when
        List<Voucher> all = repository.findAll();
        // then
        assertThat(all).hasSize(2);
    }
}
