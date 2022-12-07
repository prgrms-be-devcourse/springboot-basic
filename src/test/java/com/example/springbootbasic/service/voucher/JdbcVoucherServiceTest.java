package com.example.springbootbasic.service.voucher;

import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherFactory;
import com.example.springbootbasic.domain.voucher.VoucherType;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Stream;

import static com.example.springbootbasic.domain.voucher.VoucherType.FIXED_AMOUNT;
import static com.example.springbootbasic.domain.voucher.VoucherType.PERCENT_DISCOUNT;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class JdbcVoucherServiceTest {

    private final LocalDateTime startAt = LocalDateTime.of(2022, Month.OCTOBER, 25, 0, 0);
    private final LocalDateTime endAt = LocalDateTime.of(2022, Month.DECEMBER, 25, 0, 0);

    @Autowired
    private JdbcVoucherService voucherService;

    @BeforeAll
    static void setup() {
        MysqldConfig config = MysqldConfig.aMysqldConfig(Version.v8_0_11)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        EmbeddedMysql.anEmbeddedMysql(config)
                .addSchema("test_voucher", ScriptResolver.classPathScript("schema.sql"))
                .start();
    }

    @AfterEach
    void clearData() {
        voucherService.deleteAllVouchers();
    }

    @ParameterizedTest(name = "[{index}] discountValues = {0}, voucherTypes = {1}")
    @MethodSource("voucherDummy")
    @DisplayName("여러 타입의 바우처 저장 후 모든 바우처 검색을 성공한다.")
    void whenFindAllVouchersThenSuccessTest(List<Long> discountValues, List<VoucherType> voucherTypes) {
        // given
        int voucherSize = discountValues.size();
        for (int currVoucherIndex = 0; currVoucherIndex < voucherSize; currVoucherIndex++) {
            Voucher voucher = VoucherFactory.of(discountValues.get(currVoucherIndex), voucherTypes.get(currVoucherIndex), LocalDateTime.now(), startAt, endAt);
            voucherService.saveVoucher(voucher);
        }

        // when
        List<Voucher> allVouchers = voucherService.findAllVouchers();

        //then
        assertThat(allVouchers.size()).isEqualTo(voucherSize);
    }

    @ParameterizedTest(name = "[{index}] discountValue = {0}, voucherType = {1}")
    @MethodSource("voucherDummy")
    @DisplayName("여러 타입의 바우처 저장 후 바우처 타입별 검색을 성공한다.")
    void whenFindAllVouchersByTypeThenSuccessTest(List<Long> discountValues, List<VoucherType> voucherTypes) {
        // given
        int voucherSize = discountValues.size();
        for (int currVoucherIndex = 0; currVoucherIndex < voucherSize; currVoucherIndex++) {
            Voucher voucher = VoucherFactory.of(discountValues.get(currVoucherIndex), voucherTypes.get(currVoucherIndex), LocalDateTime.now(), startAt, endAt);
            voucherService.saveVoucher(voucher);
        }

        // when
        int inputFixedVoucherCount = (int) voucherTypes.stream()
                .filter(voucherType -> voucherType == FIXED_AMOUNT)
                .count();
        int inputPercentVoucherCount = (int) voucherTypes.stream()
                .filter(voucherType -> voucherType == PERCENT_DISCOUNT)
                .count();

        List<Voucher> allVouchers = voucherService.findAllVouchers();
        System.out.println("allVouchers = " + allVouchers);
        int fixedVoucherCount = (int) allVouchers.stream()
                .filter(voucher -> voucher.getVoucherType() == FIXED_AMOUNT)
                .count();
        int percentVoucherCount = (int) allVouchers.stream()
                .filter(voucher -> voucher.getVoucherType() == PERCENT_DISCOUNT)
                .count();

        //then
        assertThat(fixedVoucherCount).isEqualTo(inputFixedVoucherCount);
        assertThat(percentVoucherCount).isEqualTo(inputPercentVoucherCount);
    }

    @Test
    @DisplayName("바우처 타입을 이용해서 같은 타입의 모든 바우처 검색에 성공한다.")
    void whenFindAllVoucherByVoucherTypeThenSuccessTest() {
        // given
        Voucher voucher1 = VoucherFactory.of(1L, FIXED_AMOUNT, LocalDateTime.now(), startAt, endAt);
        Voucher voucher2 = VoucherFactory.of(10L, FIXED_AMOUNT, LocalDateTime.now(), startAt, endAt);
        Voucher voucher3 = VoucherFactory.of(100L, FIXED_AMOUNT, LocalDateTime.now(), startAt, endAt);
        Voucher voucher4 = VoucherFactory.of(1000L, FIXED_AMOUNT, LocalDateTime.now(), startAt, endAt);

        // when
        voucherService.saveVoucher(voucher1);
        voucherService.saveVoucher(voucher2);
        voucherService.saveVoucher(voucher3);
        voucherService.saveVoucher(voucher4);
        List<Voucher> findVouchers = voucherService.findAllVoucherByVoucherType(FIXED_AMOUNT);

        // then
        assertThat(findVouchers).hasSize(4);
    }

    @Test
    @DisplayName("바우처 아이디를 이용해서 저장된 바우처 검색에 성공한다.")
    void whenFindVoucherByIdThenSuccessTest() {
        // given
        Voucher voucher = VoucherFactory.of(12345L, FIXED_AMOUNT, LocalDateTime.now(), startAt, endAt);
        Voucher savedVoucher = voucherService.saveVoucher(voucher);

        // when
        Voucher findVoucher = voucherService.findById(savedVoucher.getVoucherId());
        System.out.println("findVoucher = " + findVoucher);

        // then
        assertThat(savedVoucher.getDiscountValue()).isEqualTo(findVoucher.getDiscountValue());
    }

    @Test
    @DisplayName("기존에 저장된 바우처 수정에 성공한다.")
    void whenUpdateVoucherThenSuccessTest() {
        // given
        Voucher voucher = VoucherFactory.of(100L, FIXED_AMOUNT, LocalDateTime.now(), startAt, endAt);
        Voucher savedVoucher = voucherService.saveVoucher(voucher);
        Voucher goingToUpdateVoucher =
                VoucherFactory.of(savedVoucher.getVoucherId(), savedVoucher.getDiscountValue(), PERCENT_DISCOUNT, LocalDateTime.now(), startAt, endAt);

        // when
        Voucher updatedVoucher = voucherService.update(goingToUpdateVoucher);

        // then
        assertThat(updatedVoucher.getVoucherType()).isEqualTo(PERCENT_DISCOUNT);
    }

    @Test
    @DisplayName("모든 바우처 삭제에 성공한다.")
    void whenDeleteAllVouchersThenSuccessTest() {
        // given
        Voucher voucher1 = VoucherFactory.of(1L, FIXED_AMOUNT, LocalDateTime.now(), startAt, endAt);
        Voucher voucher2 = VoucherFactory.of(10L, FIXED_AMOUNT, LocalDateTime.now(), startAt, endAt);
        Voucher voucher3 = VoucherFactory.of(50L, PERCENT_DISCOUNT, LocalDateTime.now(), startAt, endAt);
        voucherService.saveVoucher(voucher1);
        voucherService.saveVoucher(voucher2);
        voucherService.saveVoucher(voucher3);

        // when
        voucherService.deleteAllVouchers();
        List<Voucher> allVouchers = voucherService.findAllVouchers();

        // then
        assertThat(allVouchers).isEmpty();
    }

    @Test
    @DisplayName("입력된 바우처 타입과 같은 모든 바우처 타입 삭제에 성공한다.")
    void deleteVouchersByVoucherType() {
        // given
        Voucher voucher1 = VoucherFactory.of(1L, FIXED_AMOUNT, LocalDateTime.now(), startAt, endAt);
        Voucher voucher2 = VoucherFactory.of(10L, FIXED_AMOUNT, LocalDateTime.now(), startAt, endAt);
        Voucher voucher3 = VoucherFactory.of(100L, FIXED_AMOUNT, LocalDateTime.now(), startAt, endAt);

        // when
        voucherService.saveVoucher(voucher1);
        voucherService.saveVoucher(voucher2);
        voucherService.saveVoucher(voucher3);
        voucherService.deleteVouchersByVoucherType(FIXED_AMOUNT);
        List<Voucher> findAllVouchers = voucherService.findAllVouchers();

        // then
        assertThat(findAllVouchers).isEmpty();
    }

    static Stream<Arguments> voucherDummy() {
        return Stream.of(
                Arguments.arguments(
                        List.of(1L, 10L, 100L, 1000L, 10000L, 20000L),
                        List.of(FIXED_AMOUNT, FIXED_AMOUNT, FIXED_AMOUNT, FIXED_AMOUNT, FIXED_AMOUNT, FIXED_AMOUNT)
                ),
                Arguments.arguments(
                        List.of(30000L, 40000L, 49999L, 50L, 1L, 10L),
                        List.of(FIXED_AMOUNT, FIXED_AMOUNT, FIXED_AMOUNT, PERCENT_DISCOUNT, PERCENT_DISCOUNT, PERCENT_DISCOUNT)
                ),
                Arguments.arguments(
                        List.of(10L, 20L, 30L, 40L, 99L, 100L),
                        List.of(PERCENT_DISCOUNT, PERCENT_DISCOUNT, PERCENT_DISCOUNT, PERCENT_DISCOUNT, PERCENT_DISCOUNT, PERCENT_DISCOUNT)
                )
        );
    }
}