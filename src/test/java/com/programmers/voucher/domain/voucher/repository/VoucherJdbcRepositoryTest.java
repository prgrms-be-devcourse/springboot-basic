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
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    @DisplayName("성공: voucher 단건 저장")
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
    @DisplayName("성공: voucher 목록 조회")
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

    @Test
    @DisplayName("성공: voucher 단건 조회")
    void findById() {
        //given
        FixedAmountVoucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
        voucherJdbcRepository.save(fixedVoucher);

        //when
        Optional<Voucher> optionalVoucher = voucherJdbcRepository.findById(fixedVoucher.getVoucherId());

        //then
        assertThat(optionalVoucher).isNotEmpty();
        Voucher findVoucher = optionalVoucher.get();
        assertThat(findVoucher).usingRecursiveComparison().isEqualTo(findVoucher);
    }

    @Test
    @DisplayName("성공: voucher 단건 조회 - 존재하지 않는 voucher")
    void findById_ButEmpty() {
        //given
        FixedAmountVoucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);

        //when
        Optional<Voucher> optionalVoucher = voucherJdbcRepository.findById(fixedVoucher.getVoucherId());

        //then
        assertThat(optionalVoucher).isEmpty();
    }

    @Test
    @DisplayName("성공: voucher 전체 삭제")
    void deleteAll() {
        //given
        FixedAmountVoucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
        PercentDiscountVoucher percentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10);
        voucherJdbcRepository.save(fixedVoucher);
        voucherJdbcRepository.save(percentVoucher);

        //when
        voucherJdbcRepository.deleteAll();

        //then
        List<Voucher> findVouchers = voucherJdbcRepository.findAll();
        assertThat(findVouchers).isEmpty();
    }

    @Test
    @DisplayName("성공: voucher 단건 삭제")
    void deleteById() {
        //given
        FixedAmountVoucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
        voucherJdbcRepository.save(fixedVoucher);

        //when
        voucherJdbcRepository.deleteById(fixedVoucher.getVoucherId());

        //then
        Optional<Voucher> optionalVoucher = voucherJdbcRepository.findById(fixedVoucher.getVoucherId());
        assertThat(optionalVoucher).isEmpty();
    }

    @Test
    @DisplayName("예외: voucher 단건 삭제 - 존재하지 않는 voucher")
    void deleteById_ButNoSuchElement_Then_Exception() {
        //given
        UUID voucherId = UUID.randomUUID();

        //when
        //then
        assertThatThrownBy(() -> voucherJdbcRepository.deleteById(voucherId))
                .isInstanceOf(IncorrectResultSizeDataAccessException.class);
    }
}