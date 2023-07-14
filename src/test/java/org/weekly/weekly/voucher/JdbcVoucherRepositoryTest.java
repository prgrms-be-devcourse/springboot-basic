package org.weekly.weekly.voucher;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.weekly.weekly.voucher.domain.DiscountType;
import org.weekly.weekly.voucher.domain.Voucher;
import org.weekly.weekly.voucher.repository.JdbcVoucherRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;


@ActiveProfiles("test")
@Testcontainers
@SpringBootTest
class JdbcVoucherRepositoryTest{
    @Autowired
    private JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    Voucher fixedVoucher;
    Voucher percentVoucher;

    @BeforeEach
    void setUp() {
        fixedVoucher = Voucher.of(UUID.randomUUID(), 10000, LocalDate.now(), 7, DiscountType.FIXED);
        percentVoucher = Voucher.of(UUID.randomUUID(), 50, LocalDate.now(), 7, DiscountType.PERCENT);
    }

    @AfterEach
    void initDB() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "vouchers");
    }

    @Test
    void 전체_바우처_검색_테스트() {
        // Given
        jdbcVoucherRepository.insert(fixedVoucher);
        jdbcVoucherRepository.insert(percentVoucher);

        // When
        List<Voucher> vouchers = jdbcVoucherRepository.findAll();

        // Then
        assertThat(vouchers.isEmpty(), is(false));
    }

    @Test
    void 아이디를_통한_검색_실패_테스트() {
        // When
        Optional<Voucher> voucher = jdbcVoucherRepository.findById(fixedVoucher.getVoucherId());

        // Then
        assertThat(voucher.isEmpty(), is(true));
    }

    @Test
    void 아이디를_통한_검색_성공_테스트() {
        // Given
        jdbcVoucherRepository.insert(fixedVoucher);

        // When
        Optional<Voucher> voucher = jdbcVoucherRepository.findById(fixedVoucher.getVoucherId());

        // Then
        assertThat(voucher.isPresent(), is(true));
    }

    @Test
    void 고정_할인정책을_검색_테스트() {
        // Given
        jdbcVoucherRepository.insert(fixedVoucher);

        // When
        List<Voucher> vouchers = jdbcVoucherRepository.findByDiscountType(DiscountType.FIXED);

        // Then
        assertThat(vouchers.isEmpty(), is(false));
    }

    @Test
    void 퍼센트_할인정책을_검색_테스트() {
        // Given
        jdbcVoucherRepository.insert(percentVoucher);

        // When
        List<Voucher> vouchers = jdbcVoucherRepository.findByDiscountType(DiscountType.PERCENT);

        // Then
        assertThat(vouchers.isEmpty(), is(false));
    }

    @Test
    void 할인바우처_등록성공_테스트() {
        // Given
        Voucher voucher = jdbcVoucherRepository.insert(percentVoucher);

        // When
        Optional<Voucher> findVoucher = jdbcVoucherRepository.findById(percentVoucher.getVoucherId());

        // Then
        assertThat(findVoucher.isEmpty(), is(false));
        assertThat(findVoucher.get(), samePropertyValuesAs(voucher));
    }

    @Test
    void 고정바우처_등록성공_테스트() {
        // Given
        Voucher voucher = jdbcVoucherRepository.insert(fixedVoucher);

        // When
        Optional<Voucher> findVoucher = jdbcVoucherRepository.findById(fixedVoucher.getVoucherId());

        // Then
        assertThat(findVoucher.isEmpty(), is(false));
        assertThat(findVoucher.get(), samePropertyValuesAs(voucher));
    }

    @ParameterizedTest
    @CsvSource({"5000, 0", "15000, 5000"})
    void 고정바우처_정보_업데이트_테스트(int amount, long reaminExpected) {
        // Given
        jdbcVoucherRepository.insert(fixedVoucher);

        // When
        long remain = fixedVoucher.applyDiscount(amount);
        jdbcVoucherRepository.update(fixedVoucher);
        Optional<Voucher> voucher = jdbcVoucherRepository.findById(fixedVoucher.getVoucherId());

        // Then
        assertThat(remain, is(reaminExpected));
        assertThat(voucher.isEmpty(), is(false));
        assertThat(voucher.get(), samePropertyValuesAs(fixedVoucher));
    }

    @ParameterizedTest
    @CsvSource({"3000, 1500", "1000, 500"})
    void 퍼센트바우처_정보_업데이트_테스트(int amount, long reaminExpected) {
        // Given
        jdbcVoucherRepository.insert(percentVoucher);

        // When
        long remain = percentVoucher.applyDiscount(amount);
        jdbcVoucherRepository.update(percentVoucher);
        Optional<Voucher> voucher = jdbcVoucherRepository.findById(percentVoucher.getVoucherId());

        // Then
        assertThat(remain, is(reaminExpected));
        assertThat(voucher.isEmpty(), is(false));
        assertThat(voucher.get(), samePropertyValuesAs(percentVoucher));
    }

    @Test
    void 바우처_삭제_테스트() {
        // Given
        Voucher voucher = jdbcVoucherRepository.insert(percentVoucher);
        Optional<Voucher> findVoucher = jdbcVoucherRepository.findById(voucher.getVoucherId());
        assertThat(findVoucher.isPresent(), is(true));

        // when
        jdbcVoucherRepository.deleteById(voucher.getVoucherId());

        // Then
        Optional<Voucher> deleteVoucher = jdbcVoucherRepository.findById(voucher.getVoucherId());
        assertThat(deleteVoucher.isEmpty(), is(true));
    }

    @Test
    void 전체_바우처_삭제_테스트() {
        // Given
        jdbcVoucherRepository.insert(percentVoucher);
        jdbcVoucherRepository.insert(fixedVoucher);

        // When
        jdbcVoucherRepository.deleteAll();
        List<Voucher> vouchers = jdbcVoucherRepository.findAll();

        // Then
        assertThat(vouchers.isEmpty(), is(true));
    }
}
