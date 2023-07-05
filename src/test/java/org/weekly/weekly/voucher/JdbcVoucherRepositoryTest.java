package org.weekly.weekly.voucher;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.weekly.weekly.voucher.domain.DiscountType;
import org.weekly.weekly.voucher.domain.Voucher;
import org.weekly.weekly.voucher.repository.JdbcVoucherRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("test")
@SpringBootTest
class JdbcVoucherRepositoryTest {
    @Autowired
    private JdbcVoucherRepository jdbcVoucherRepository;

    static Voucher fixedVoucher;
    static Voucher percentVoucher;

    @BeforeAll
    static void setUp() {
        fixedVoucher = Voucher.of(UUID.randomUUID(), 10000, LocalDate.now(), 7, DiscountType.FIXED);
        percentVoucher = Voucher.of(UUID.randomUUID(), 50, LocalDate.now(), 7, DiscountType.PERCENT);
    }

    @Test
    void 아이디를_통한_검색_실패_테스트() {
        Optional<Voucher> voucher = jdbcVoucherRepository.findById(fixedVoucher.getVoucherId());
        assertThat(voucher.isEmpty(), is(true));
    }

    @Test
    void 아이디를_통한_검색_성공_테스트() {
        Optional<Voucher> voucher = jdbcVoucherRepository.findById(fixedVoucher.getVoucherId());
        assertThat(voucher.isPresent(), is(true));
    }

    @Test
    void 할인정책을_통한_검색_테스트() {
        List<Voucher> vouchers = jdbcVoucherRepository.findByDiscountType(percentVoucher);

        assertThat(vouchers.isEmpty(), is(false));

        vouchers.stream()
                .forEach(voucher -> assertThat(voucher.getDiscountType(), is(DiscountType.PERCENT)));
    }

    @Test
    void 전체_바우처_검색_테스트() {
        List<Voucher> vouchers = jdbcVoucherRepository.findAll();

        assertThat(vouchers.isEmpty(), is(false));
    }

    @Test
    void 할인바우처_등록성공_테스트() {
        Voucher voucher = jdbcVoucherRepository.insert(percentVoucher);

        Optional<Voucher> findVoucher = jdbcVoucherRepository.findById(percentVoucher.getVoucherId());

        assertThat(findVoucher.isEmpty(), is(false));
        assertThat(findVoucher.get(), samePropertyValuesAs(voucher));
    }

    @Test
    void 고정바우처_등록성공_테스트() {
        Voucher voucher = jdbcVoucherRepository.insert(fixedVoucher);

        Optional<Voucher> findVoucher = jdbcVoucherRepository.findById(fixedVoucher.getVoucherId());

        assertThat(findVoucher.isEmpty(), is(false));
        assertThat(findVoucher.get(), samePropertyValuesAs(voucher));
    }


    @Test
    void 바우처_정보_업데이트_테스트() {
        // Given
        jdbcVoucherRepository.insert(fixedVoucher);

        // When
        long remain = fixedVoucher.applyDiscount(5000);
        jdbcVoucherRepository.update(fixedVoucher);
        Optional<Voucher> voucher = jdbcVoucherRepository.findById(fixedVoucher.getVoucherId());

        // Then
        assertThat(remain, is(5000));
        assertThat(voucher.isEmpty(), is(false));
        assertThat(voucher.get(), samePropertyValuesAs(fixedVoucher));

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
        jdbcVoucherRepository.deleteAll();

        // when
        List<Voucher> vouchers = jdbcVoucherRepository.findAll();
        assertThat(vouchers.isEmpty(), is(true));
    }
}
