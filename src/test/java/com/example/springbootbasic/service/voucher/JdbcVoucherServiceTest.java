package com.example.springbootbasic.service.voucher;

import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherFactory;
import com.example.springbootbasic.domain.voucher.VoucherType;
import com.example.springbootbasic.service.customer.JdbcCustomerService;
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
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.stream.Stream;

import static com.example.springbootbasic.domain.voucher.VoucherType.FIXED_AMOUNT;
import static com.example.springbootbasic.domain.voucher.VoucherType.PERCENT_DISCOUNT;
import static com.example.springbootbasic.repository.voucher.VoucherParam.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class JdbcVoucherServiceTest {

    @Autowired
    private JdbcVoucherService voucherService;
    @Autowired
    private JdbcCustomerService customerService;

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
        customerService.deleteAllCustomerVoucher();
        customerService.deleteAllCustomers();
        voucherService.deleteAllVouchers();
    }

    @ParameterizedTest(name = "[{index}] discountValues = {0}, voucherTypes = {1}")
    @MethodSource("voucherDummy")
    @DisplayName("여러 타입의 바우처 저장 후 모든 바우처 검색을 성공한다.")
    void whenFindAllVouchersThenSuccessTest(List<Long> discountValues, List<VoucherType> voucherTypes) {
        // given
        int voucherSize = discountValues.size();
        for (int currVoucherIndex = 0; currVoucherIndex < voucherSize; currVoucherIndex++) {
            Voucher voucher = VoucherFactory.of(discountValues.get(currVoucherIndex), voucherTypes.get(currVoucherIndex));
            voucherService.saveVoucher(voucher);
        }

        // when
        List<Voucher> allVouchers = voucherService.findAllVouchers();

        //then
        assertThat(allVouchers.size()).isEqualTo(voucherSize);
    }

    @Test
    @DisplayName(" 바우처 타입별 검색을 성공한다.")
    @Sql(value = {"classpath:vouchers-dummy.sql", "classpath:customers-dummy.sql"})

    void whenFindAllVouchersByTypeThenSuccessTest() {
        boolean isAllFixedAmount = voucherService.findAllVoucherByVoucherType(FIXED_AMOUNT)
                .stream()
                .allMatch(voucher -> voucher.getVoucherType() == FIXED_AMOUNT);
        boolean isAllPercent = voucherService.findAllVoucherByVoucherType(PERCENT_DISCOUNT)
                .stream()
                .allMatch(voucher -> voucher.getVoucherType() == PERCENT_DISCOUNT);

        //then
        assertThat(isAllFixedAmount).isTrue();
        assertThat(isAllPercent).isTrue();
    }

    @Test
    @DisplayName("바우처 타입을 이용해서 같은 타입의 모든 바우처 검색에 성공한다.")
    @Sql(value = "classpath:vouchers-dummy.sql")
    void whenFindAllVoucherByVoucherTypeThenSuccessTest() {
        // when
        List<Voucher> findVouchers = voucherService.findAllVoucherByVoucherType(FIXED_AMOUNT);

        // then
        assertThat(findVouchers).hasSize(3);
    }

    @Test
    @DisplayName("바우처 아이디를 이용해서 저장된 바우처 검색에 성공한다.")
    void whenFindVoucherByIdThenSuccessTest() {
        // given
        Voucher voucher = VoucherFactory.of(12345L, FIXED_AMOUNT);
        Voucher savedVoucher = voucherService.saveVoucher(voucher);

        // when
        Voucher findVoucher = voucherService.findById(savedVoucher.getVoucherId());

        // then
        assertThat(savedVoucher.getVoucherDiscountValue()).isEqualTo(findVoucher.getVoucherDiscountValue());
    }

    @Test
    @DisplayName("기존에 저장된 바우처 수정에 성공한다.")
    @Sql(value = "classpath:vouchers-dummy.sql")
    void whenUpdateVoucherThenSuccessTest() {
        // given
        Voucher goingToUpdateVoucher =
                VoucherFactory.of(1L, 50000L, FIXED_AMOUNT);

        // when
        Voucher updatedVoucher = voucherService.update(goingToUpdateVoucher);

        // then
        assertThat(updatedVoucher)
                .hasFieldOrPropertyWithValue(VOUCHER_ID.getParam(), 1L)
                .hasFieldOrPropertyWithValue(VOUCHER_DISCOUNT_VALUE.getParam(), 50000L)
                .hasFieldOrPropertyWithValue(VOUCHER_TYPE.getParam(), FIXED_AMOUNT);

    }

    @Test
    @DisplayName("모든 바우처 삭제에 성공한다.")
    @Sql(value = "classpath:vouchers-dummy.sql")
    void whenDeleteAllVouchersThenSuccessTest() {
        // when
        voucherService.deleteAllVouchers();
        List<Voucher> allVouchers = voucherService.findAllVouchers();

        // then
        assertThat(allVouchers).isEmpty();
    }

    @Test
    @DisplayName("입력된 바우처 타입과 같은 모든 바우처 타입 삭제에 성공한다.")
    @Sql(value = "classpath:vouchers-dummy.sql")
    void deleteVouchersByVoucherType() {
        // when
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