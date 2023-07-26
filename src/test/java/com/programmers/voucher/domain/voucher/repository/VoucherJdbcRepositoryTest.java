package com.programmers.voucher.domain.voucher.repository;

import com.programmers.voucher.domain.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.domain.Voucher;
import com.programmers.voucher.domain.voucher.domain.VoucherType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.programmers.voucher.testutil.VoucherTestUtil.createFixedVoucher;
import static com.programmers.voucher.testutil.VoucherTestUtil.createPercentVoucher;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
class VoucherJdbcRepositoryTest {

    @Autowired
    private VoucherJdbcRepository voucherJdbcRepository;

    FixedAmountVoucher givenFixedVoucher;
    PercentDiscountVoucher givenPercentVoucher;

    @TestConfiguration
    static class Config {
        @Bean
        public VoucherJdbcRepository voucherJdbcRepository(NamedParameterJdbcTemplate template) {
            return new VoucherJdbcRepository(template);
        }
    }

    @BeforeEach
    void init() {
        givenFixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
        givenPercentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10);
        voucherJdbcRepository.save(givenFixedVoucher);
        voucherJdbcRepository.save(givenPercentVoucher);
    }

    @Test
    @DisplayName("성공: voucher 단건 저장")
    void save() {
        //given
        FixedAmountVoucher fixedVoucher = createFixedVoucher();

        //when
        voucherJdbcRepository.save(fixedVoucher);

        //then
        Optional<Voucher> optionalVoucher = voucherJdbcRepository.findById(fixedVoucher.getVoucherId());
        assertThat(optionalVoucher).isNotEmpty();
        Voucher findVoucher = optionalVoucher.get();
        assertThat(findVoucher).usingRecursiveComparison().isEqualTo(fixedVoucher);
    }

    @Test
    @DisplayName("성공: voucher 목록 조회")
    void findAll() {
        //given
        FixedAmountVoucher fixedVoucher = createFixedVoucher();
        PercentDiscountVoucher percentVoucher = createPercentVoucher();
        voucherJdbcRepository.save(fixedVoucher);
        voucherJdbcRepository.save(percentVoucher);

        //when
        List<Voucher> findVouchers = voucherJdbcRepository.findAll();

        //then
        assertThat(findVouchers).usingRecursiveFieldByFieldElementComparator()
                .contains(fixedVoucher, percentVoucher);
    }

    @Test
    @DisplayName("성공: voucher 조건 조회 - 바우처 타입")
    void findAll_VoucherType() {
        //given
        Voucher fixedVoucher = createFixedVoucher();
        Voucher percentVoucher = createPercentVoucher();
        voucherJdbcRepository.save(fixedVoucher);
        voucherJdbcRepository.save(percentVoucher);

        VoucherType voucherType = VoucherType.FIXED_AMOUNT;

        //when
        List<Voucher> result = voucherJdbcRepository.findAll(voucherType, null, null);

        //then
        assertThat(result).usingRecursiveFieldByFieldElementComparator()
                .contains(fixedVoucher)
                .doesNotContain(percentVoucher);
    }

    @Test
    @DisplayName("성공: voucher 조건 조회 - 생성 기간")
    void findAll_CreatedAt() {
        //given
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = LocalDateTime.now().plusHours(2);
        FixedAmountVoucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), startTime, 10);
        voucherJdbcRepository.save(fixedVoucher);

        //when
        List<Voucher> result = voucherJdbcRepository.findAll(null, startTime, endTime);

        //then
        assertThat(result).usingRecursiveFieldByFieldElementComparator()
                .containsExactly(fixedVoucher)
                .doesNotContain(givenFixedVoucher, givenPercentVoucher);
    }

    @Test
    @DisplayName("성공: voucher 조건 조회 - 바우처 타입, 생성 기간")
    void findAll_VoucherTypeAndCreatedAt() {
        //given
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = LocalDateTime.now().plusHours(2);
        FixedAmountVoucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), startTime, 10);
        PercentDiscountVoucher percentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), startTime, 10);
        voucherJdbcRepository.save(fixedVoucher);
        voucherJdbcRepository.save(percentVoucher);

        //when
        List<Voucher> result = voucherJdbcRepository.findAll(VoucherType.FIXED_AMOUNT, startTime, endTime);

        //then
        assertThat(result).usingRecursiveFieldByFieldElementComparator()
                .containsExactly(fixedVoucher)
                .doesNotContain(percentVoucher, givenFixedVoucher, givenPercentVoucher);
    }

    @Test
    @DisplayName("성공: voucher 단건 조회")
    void findById() {
        //given
        FixedAmountVoucher fixedVoucher = createFixedVoucher();
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
        UUID voucherId = UUID.randomUUID();

        //when
        Optional<Voucher> optionalVoucher = voucherJdbcRepository.findById(voucherId);

        //then
        assertThat(optionalVoucher).isEmpty();
    }

    @Test
    @DisplayName("성공: voucher 단건 삭제")
    void deleteById() {
        //given
        FixedAmountVoucher fixedVoucher = createFixedVoucher();
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