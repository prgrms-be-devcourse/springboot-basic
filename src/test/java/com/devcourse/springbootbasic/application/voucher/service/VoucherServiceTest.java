package com.devcourse.springbootbasic.application.voucher.service;

import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.voucher.model.Voucher;
import com.devcourse.springbootbasic.application.voucher.model.DiscountValue;
import com.devcourse.springbootbasic.application.voucher.model.VoucherType;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("default")
class VoucherServiceTest {

    @Autowired
    VoucherService voucherService;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void init() {
        var mysqlConfig = MysqldConfig.aMysqldConfig(Version.v8_0_17)
                .withCharset(Charset.UTF8)
                .withTimeZone("Asia/Seoul")
                .withUser("test", "test1234!")
                .withPort(8070)
                .build();
        embeddedMysql = EmbeddedMysql.anEmbeddedMysql(mysqlConfig)
                .addSchema("test-voucher_system", ScriptResolver.classPathScript("test-voucher_schema.sql"))
                .start();
    }

    @BeforeEach
    void cleanup() {
        voucherService.deleteAllVouchers();
    }

    @AfterAll
    void destroy() {
        embeddedMysql.stop();
    }

    @ParameterizedTest
    @DisplayName("새로운 바우처가 추가되면 성공한다.")
    @MethodSource("provideVouchers")
    void createVoucher_VoucherParam_InsertAndReturnVoucher(Voucher voucher) {
        voucherService.createVoucher(voucher);
        var result = voucherService.findVoucherById(voucher.getVoucherId());
        assertThat(result, notNullValue());
        assertThat(result, instanceOf(Voucher.class));
        assertThat(result.getVoucherId(), is(voucher.getVoucherId()));
    }

    @ParameterizedTest
    @DisplayName("이미 존재하는 바우처를 또 생성하려고 하면 실패한다.")
    @MethodSource("provideVouchers")
    void createVoucher_ParamExistVoucher_Exception(Voucher voucher) {
        voucherService.createVoucher(voucher);
        Assertions.assertThrows(InvalidDataException.class, () -> voucherService.createVoucher(voucher));
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 갱신하려고 하면 성공한다.")
    @MethodSource("provideVouchers")
    void updateVoucher_ParamExistVoucher_UpdateAndReturnVoucher(Voucher voucher) {
        voucherService.createVoucher(voucher);
        var newVoucher = new Voucher(voucher.getVoucherId(), VoucherType.FIXED_AMOUNT, DiscountValue.from(VoucherType.FIXED_AMOUNT, 124));
        voucherService.updateVoucher(newVoucher);
        var result = voucherService.findVoucherById(newVoucher.getVoucherId());
        assertThat(result, samePropertyValuesAs(newVoucher));
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 바우처를 갱신하려고 하면 실패한다.")
    @MethodSource("provideVouchers")
    void updateVoucher_ParamNotExistVoucher_UpdateAndReturnVoucher(Voucher voucher) {
        Assertions.assertThrows(InvalidDataException.class, () -> voucherService.updateVoucher(voucher));
    }

    @Test
    @DisplayName("생성된 바우처가 리스트 형태로 반환되면 성공한다.")
    void getVouchers_VoucherMap_ReturnVouchers() {
        voucherService.createVoucher(vouchers.get(0));
        var result = voucherService.getVouchers();
        assertThat(result, notNullValue());
        assertThat(result, not(empty()));
        assertThat(result, instanceOf(List.class));
        assertThat(result.get(0), instanceOf(Voucher.class));
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 아이디로 검색하는 경우 성공한다.")
    @MethodSource("provideVouchers")
    void findVoucherById_ParamExistVoucher_ReturnVoucher(Voucher voucher) {
        voucherService.createVoucher(voucher);
        var foundVoucher = voucherService.findVoucherById(voucher.getVoucherId());
        assertThat(foundVoucher, samePropertyValuesAs(voucher));
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 바우처를 아이디로 검색하는 경우 실패한다.")
    @MethodSource("provideVouchers")
    void findVoucherById_ParamNotExistVoucher_Exception(Voucher voucher) {
        Assertions.assertThrows(InvalidDataException.class, () -> voucherService.findVoucherById(voucher.getVoucherId()));
    }

    @Test
    @DisplayName("모든 바우처를 제거한다.")
    void deleteAllVouchers_ParamVoid_DeleteAllVouchers() {
        voucherService.deleteAllVouchers();
        var vouchers = voucherService.getVouchers();
        assertThat(vouchers.isEmpty(), is(true));
    }

    @ParameterizedTest
    @DisplayName("아이디로 바우처 제거한다.")
    @MethodSource("provideVouchers")
    void deleteVoucherById_PararmVoucher_DeleteVoucher(Voucher voucher) {
        voucherService.createVoucher(voucher);
        var deletedVoucher = voucherService.deleteVoucherById(voucher.getVoucherId());
        assertThat(deletedVoucher, samePropertyValuesAs(voucher));
    }

    static List<Voucher> vouchers = List.of(
            new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, DiscountValue.from(VoucherType.FIXED_AMOUNT, "100")),
            new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, DiscountValue.from(VoucherType.PERCENT_DISCOUNT, "0")),
            new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, DiscountValue.from(VoucherType.FIXED_AMOUNT, "1240")),
            new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, DiscountValue.from(VoucherType.PERCENT_DISCOUNT, "10"))
    );

    static Stream<Arguments> provideVouchers() {
        return vouchers.stream()
                .map(Arguments::of);
    }

}
