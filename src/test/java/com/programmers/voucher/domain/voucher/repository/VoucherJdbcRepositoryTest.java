package com.programmers.voucher.domain.voucher.repository;

import com.programmers.voucher.domain.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.domain.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class VoucherJdbcRepositoryTest {

    @Autowired
    private VoucherJdbcRepository voucherJdbcRepository;

    @TestConfiguration
    static class Config {
        @Bean
        public VoucherJdbcRepository voucherJdbcRepository(DataSource dataSource) {
            return new VoucherJdbcRepository(dataSource);
        }
    }

    @Test
    @DisplayName("성공 - voucher 단건 저장")
    void save() {
        //given
        FixedAmountVoucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);

        //when
        voucherJdbcRepository.save(fixedVoucher);

        //then
        List<Voucher> findVouchers = voucherJdbcRepository.findAll();

        assertThat(findVouchers.size()).isEqualTo(1);
        assertThat(findVouchers.get(0)).usingRecursiveComparison().isEqualTo(fixedVoucher);
    }

    @Test
    @DisplayName("성공 - voucher 목록 조회")
    void findAll() {
        //given
        FixedAmountVoucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
        PercentDiscountVoucher percentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10);
        voucherJdbcRepository.save(fixedVoucher);
        voucherJdbcRepository.save(percentVoucher);

        //when
        List<Voucher> findVouchers = voucherJdbcRepository.findAll();

        //then
        assertThat(findVouchers).usingRecursiveFieldByFieldElementComparator()
                .contains(fixedVoucher, percentVoucher);
    }

}