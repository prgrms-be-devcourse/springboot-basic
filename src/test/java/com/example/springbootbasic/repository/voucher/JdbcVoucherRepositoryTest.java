package com.example.springbootbasic.repository.voucher;

import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherFactory;
import com.example.springbootbasic.domain.voucher.VoucherType;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.example.springbootbasic.domain.voucher.VoucherType.FIXED_AMOUNT;
import static com.example.springbootbasic.domain.voucher.VoucherType.PERCENT_DISCOUNT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@ActiveProfiles("test")
class JdbcVoucherRepositoryTest {

    @Autowired
    private JdbcVoucherRepository voucherRepository;

    @BeforeAll
    static void setup() {
        MysqldConfig config = MysqldConfig.aMysqldConfig(Version.v8_0_11)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        EmbeddedMysql.anEmbeddedMysql(config)
                .addSchema("test-voucher", ScriptResolver.classPathScript("schema.sql"))
                .start();
    }

    @BeforeEach
    void beforeEach() {
        voucherRepository.deleteAllVouchers();
    }

    @Test
    @DisplayName("FIXED, PERCENT 타입의 바우처를 저장하고 모든 바우처 검색을 성공한다.")
    void whenFindAllVouchersThenSuccessTest() {
        // given
        Voucher voucher1 = VoucherFactory.of(1L, FIXED_AMOUNT);
        Voucher voucher2 = VoucherFactory.of(100L, FIXED_AMOUNT);
        Voucher voucher3 = VoucherFactory.of(1L, PERCENT_DISCOUNT);
        Voucher voucher4 = VoucherFactory.of(10L, PERCENT_DISCOUNT);

        // when
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.save(voucher3);
        voucherRepository.save(voucher4);
        List<Voucher> allVouchers = voucherRepository.findAllVouchers();

        // then
        assertThat(allVouchers).hasSize(4);
    }

    @ParameterizedTest
    @MethodSource("whenSaveVoucherOverRangeThenFailDummy")
    @DisplayName("할인 금액 설정 범위를 넘은 바우처 저장을 실패한다.")
    void whenSaveVoucherOverRangeThenFailTest(long discountValue, VoucherType voucherType) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> VoucherFactory.of(discountValue, voucherType));
    }

    @Test
    @DisplayName("새로운 바우처를 저장하고, 바우처 타입 수정하여 바우처 비교를 성공한다.")
    void whenUpdateVoucherThenSuccessTest() {
        // given
        Voucher voucher = VoucherFactory.of(100L, FIXED_AMOUNT);

        // when
        Voucher savedVoucher = voucherRepository.save(voucher);
        Voucher goingToUpdateVoucher =
                VoucherFactory.of(savedVoucher.getVoucherId(), savedVoucher.getDiscountValue(), PERCENT_DISCOUNT);
        Voucher updatedVoucher = voucherRepository.update(goingToUpdateVoucher);

        // then
        assertThat(updatedVoucher.getVoucherType()).isEqualTo(PERCENT_DISCOUNT);
    }

    @Test
    @DisplayName("새로운 바우처 저장하고, 다른 바우처를 수정하려 접근하여 실패한다.")
    void whenUpdateAnotherVoucherThenFailTest() {
        // given
        Voucher voucher = VoucherFactory.of(100L, FIXED_AMOUNT);
        Voucher anotherVoucher = VoucherFactory.of(100L, FIXED_AMOUNT);

        // when
        Voucher savedVoucher = voucherRepository.save(voucher);
        Voucher savedAnotherVoucher = voucherRepository.save(anotherVoucher);

        Voucher goingToUpdateVoucher =
                VoucherFactory.of(savedAnotherVoucher.getVoucherId(), savedVoucher.getDiscountValue(), PERCENT_DISCOUNT);
        Voucher updatedVoucher = voucherRepository.update(goingToUpdateVoucher);

        // then
        assertThat(updatedVoucher).isNotEqualTo(savedVoucher);
    }

    @Test
    @DisplayName("바우처 타입을 통해 조회시 null 타입이 들어갔을 경우 Collections.emptyList를 반환한다.")
    void whenFindVoucherTypeNullThenExceptionTest() {
        // when
        List<Voucher> findNullVouchers = voucherRepository.findAllVouchersByVoucherType(null);

        // then
        assertThat(findNullVouchers).isEqualTo(Collections.emptyList());
    }

    @Test
    @DisplayName("모든 바우처 레코드 조회를 성공한다.")
    void whenFindVoucherAllThenSuccessTest() {
        // given
        Voucher voucher1 = VoucherFactory.of(1L, FIXED_AMOUNT);
        Voucher voucher2 = VoucherFactory.of(10L, FIXED_AMOUNT);
        Voucher voucher3 = VoucherFactory.of(100L, FIXED_AMOUNT);
        Voucher voucher4 = VoucherFactory.of(100L, PERCENT_DISCOUNT);

        // when
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.save(voucher3);
        voucherRepository.save(voucher4);
        List<Voucher> findAllVouchers = voucherRepository.findAllVouchers();

        // then
        assertThat(findAllVouchers.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("여러 타입의 바우처를 저장하고 모든 바우처 삭제에 성공한다.")
    void whenDeleteVouchersThenSuccessTest() {
        // given
        Voucher voucher1 = VoucherFactory.of(1L, FIXED_AMOUNT);
        Voucher voucher2 = VoucherFactory.of(10L, FIXED_AMOUNT);
        Voucher voucher3 = VoucherFactory.of(100L, FIXED_AMOUNT);
        Voucher voucher4 = VoucherFactory.of(100L, PERCENT_DISCOUNT);

        // when
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.save(voucher3);
        voucherRepository.save(voucher4);
        voucherRepository.deleteAllVouchers();
        List<Voucher> findAllVouchers = voucherRepository.findAllVouchers();

        // then
        assertThat(findAllVouchers.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("저장된 바우처를 아이디를 통해서 검색에 성공한다.")
    void whenFindVoucherByIdThenSuccessTest() {
        // given
        Voucher voucher = VoucherFactory.of(1L, FIXED_AMOUNT);

        // when
        Voucher savedVoucher = voucherRepository.save(voucher);
        Voucher findVoucher = voucherRepository.findById(savedVoucher.getVoucherId());

        // then
        assertThat(findVoucher.getVoucherType()).isEqualTo(savedVoucher.getVoucherType());
        assertThat(findVoucher.getDiscountValue()).isEqualTo(savedVoucher.getDiscountValue());
    }

    @Test
    @DisplayName("바우처 타입이 같은 모든 바우처 삭제를 성공한다.")
    void whenDeleteAllVouchersByVoucherTypeThenSuccessTest() {
        // given
        Voucher voucher1 = VoucherFactory.of(1L, FIXED_AMOUNT);
        Voucher voucher2 = VoucherFactory.of(10L, FIXED_AMOUNT);
        Voucher voucher3 = VoucherFactory.of(100L, FIXED_AMOUNT);

        // when
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.save(voucher3);
        voucherRepository.deleteVouchersByVoucherType(FIXED_AMOUNT);
        List<Voucher> findAllVouchers = voucherRepository.findAllVouchers();

        // then
        assertThat(findAllVouchers.size()).isZero();
    }

    static Stream<Arguments> whenSaveVoucherOverRangeThenFailDummy() {
        return Stream.of(
                Arguments.arguments(-1L, FIXED_AMOUNT),
                Arguments.arguments(50001L, FIXED_AMOUNT),
                Arguments.arguments(Long.MAX_VALUE, FIXED_AMOUNT),
                Arguments.arguments(-1L, PERCENT_DISCOUNT),
                Arguments.arguments(101L, PERCENT_DISCOUNT),
                Arguments.arguments(Long.MAX_VALUE, PERCENT_DISCOUNT)
        );
    }
}