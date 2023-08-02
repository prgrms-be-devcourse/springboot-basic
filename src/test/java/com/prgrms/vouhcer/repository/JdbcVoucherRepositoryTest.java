package com.prgrms.vouhcer.repository;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import com.prgrms.common.util.Generator;
import com.prgrms.voucher.model.voucher.FixedAmountVoucher;
import com.prgrms.voucher.model.voucher.Voucher;
import com.prgrms.voucher.model.VoucherCreator;
import com.prgrms.voucher.repository.JdbcVoucherRepository;
import com.prgrms.voucher.model.VoucherType;
import com.prgrms.voucher.model.Vouchers;
import com.prgrms.voucher.model.discount.DiscountCreator;
import com.prgrms.voucher.model.discount.FixedDiscount;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({JdbcVoucherRepository.class, DiscountCreator.class, VoucherCreator.class})
class JdbcVoucherRepositoryTest {

    class TestGenerator implements Generator {

        @Override
        public String makeKey() {
            return "1";
        }

        @Override
        public LocalDateTime makeDate() {
            String str = "2023-07-29 13:47:13.248";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            return LocalDateTime.parse(str, formatter);
        }
    }

    TestGenerator testGenerator = new TestGenerator();
    final String fixVoucherId = "4";
    final String percentVoucherId = "5";
    final LocalDateTime today = testGenerator.makeDate();
    final LocalDateTime yesterday = today.minusDays(1);
    final LocalDateTime tomorrow = today.plusDays(1);

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    Voucher newTodayFixVoucher;
    Voucher newTodayPercentVoucher;

    @BeforeEach
    void clean() {
        newTodayPercentVoucher = new FixedAmountVoucher(percentVoucherId, new FixedDiscount(20),
                VoucherType.PERCENT_DISCOUNT_VOUCHER, today);

        newTodayFixVoucher = new FixedAmountVoucher(fixVoucherId, new FixedDiscount(20),
                VoucherType.FIXED_AMOUNT_VOUCHER, today);

        jdbcVoucherRepository.insert(newTodayPercentVoucher);
        jdbcVoucherRepository.insert(newTodayFixVoucher);
    }


    @Test
    @DisplayName("바우처를 추가한 결과 반환하는 바우처의 아이디와 추가한 바우처의 아이디는 같다.")
    void insert_VoucherId_EqualsNewVoucherId() {
        //when
        Optional<Voucher> retrievedVoucher = jdbcVoucherRepository.findById(
                newTodayFixVoucher.getVoucherId());

        //then
        assertThat(retrievedVoucher.isEmpty(), is(false));
        assertThat(retrievedVoucher.get().getVoucherId(),
                samePropertyValuesAs(newTodayFixVoucher.getVoucherId()));
    }

    @Test
    @DisplayName("필터에 아무런 조건을 걸지 않으면 저장소에 저장된 모든 데이터를 반환한다.")
    void findAll_NoFilter_All() {
        //when
        Vouchers vouchers = jdbcVoucherRepository.getAllVoucher(null,null);

        //then
        assertThat(vouchers.vouchers(), hasSize(2));
    }

    @Test
    @DisplayName("필터에 바우처 타입으로 고정된 바우처를 지정했을 때 고정된 바우처의 결과만을 반환한다.")
    void findAll_FilterWithFixedVoucherType_FixedVoucher() {
        //when
        Vouchers vouchers = jdbcVoucherRepository.getAllVoucher(VoucherType.FIXED_AMOUNT_VOUCHER,null);

        //then
        boolean allFixed = vouchers.vouchers().stream()
                .allMatch(voucher -> voucher.getVoucherType() == VoucherType.FIXED_AMOUNT_VOUCHER);
        assertThat(allFixed, is(true));
    }

    @Test
    @DisplayName("필터에 바우처 타입으로 할인율 바우처를 지정했을 때 할인율 바우처의 결과만을 반환한다.")
    void findAll_FilterWithPercentVoucherType_FixedVoucher() {
        //when
        Vouchers vouchers = jdbcVoucherRepository.getAllVoucher(VoucherType.PERCENT_DISCOUNT_VOUCHER,null);

        //then
        boolean allPercent = vouchers.vouchers().stream()
                .allMatch(voucher -> voucher.getVoucherType() == VoucherType.PERCENT_DISCOUNT_VOUCHER);
        assertThat(allPercent, is(true));
    }

    @Test
    @DisplayName("필터에 날짜 조건만 어제 날짜로 설졍한 경우 어제 날짜 이후로 만들어진 바우처를 반환한다.")
    void findAll_FilterWithYesterday_LaterThanYesterday() {
        //when
        Vouchers vouchers = jdbcVoucherRepository.getAllVoucher(null,yesterday);

        //then
        assertThat(vouchers.vouchers(), hasSize(2));
    }

    @Test
    @DisplayName("필터에 날짜 조건만 내일 날짜로 설졍한 경우 어떤 바우처도 반환하지 않는다.")
    void findAll_FilterWithTomorrow_Empty() {
        //when
        Vouchers vouchers = jdbcVoucherRepository.getAllVoucher(null,tomorrow);

        //then
        assertThat(vouchers.vouchers(), hasSize(0));
    }

    @Test
    @DisplayName("필터에 날짜 조건만을 걸고 그 날짜를 어제 날짜로 설정한 경우, 어제 날짜 이후로 생성된 바우처만을 반환한다")
    void findById_ExistingVoucherId_EqualsSearchId() {
        //when
        Optional<Voucher> voucher = jdbcVoucherRepository.findById(newTodayFixVoucher.getVoucherId());

        //then
        assertThat(voucher.isEmpty(), is(false));
        assertThat(voucher.get().getVoucherId()).isEqualTo(newTodayFixVoucher.getVoucherId());
    }

}
