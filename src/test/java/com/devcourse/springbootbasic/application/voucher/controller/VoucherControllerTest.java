package com.devcourse.springbootbasic.application.voucher.controller;

import com.devcourse.springbootbasic.application.customer.model.Customer;
import com.devcourse.springbootbasic.application.customer.repository.CustomerRepository;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
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

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherControllerTest {

    static List<Customer> customers = List.of(
            new Customer(UUID.randomUUID(), "사과"),
            new Customer(UUID.randomUUID(), "딸기")
    );
    static List<VoucherDto> voucherDto = List.of(
            new VoucherDto(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 23), customers.get(0).getCustomerId()),
            new VoucherDto(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, 41), customers.get(0).getCustomerId()),
            new VoucherDto(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 711), customers.get(0).getCustomerId())
    );
    @Autowired
    VoucherController voucherController;
    @Autowired
    CustomerRepository customerRepository;
    EmbeddedMysql embeddedMysql;

    static Stream<Arguments> provideVoucherDto() {
        return voucherDto.stream()
                .map(Arguments::of);
    }

    @BeforeAll
    void init() {
        var mysqlConfig = MysqldConfig.aMysqldConfig(Version.v8_0_17)
                .withPort(8070)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .withCharset(Charset.UTF8)
                .build();
        embeddedMysql = EmbeddedMysql.anEmbeddedMysql(mysqlConfig)
                .addSchema("test-voucher_system", ScriptResolver.classPathScript("test-schema.sql"))
                .start();
        customers.forEach(customer -> customerRepository.insert(customer));
    }

    @BeforeEach
    void cleanup() {
        voucherController.unregisterVouchers();
    }

    @AfterAll
    void destroy() {
        embeddedMysql.stop();
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 바우처를 생성 시 성공한다.")
    @MethodSource("provideVoucherDto")
    void createVoucher_ParamExistVoucherDto_CreateReturnVoucherDto(VoucherDto voucherDto) {
        voucherController.registerVoucher(voucherDto);
        var createdVoucher = voucherController.getVoucherById(voucherDto.voucherId());
        assertThat(createdVoucher, samePropertyValuesAs(voucherDto));
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 생성 시 실패한다.")
    @MethodSource("provideVoucherDto")
    void createVoucher_ParamNotExistVoucherDto_Exception(VoucherDto voucherDto) {
        voucherController.registerVoucher(voucherDto);
        assertThrows(InvalidDataException.class, () -> voucherController.registerVoucher(voucherDto));
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 갱신 시 성공한다.")
    @MethodSource("provideVoucherDto")
    void updateVoucher_ParamExistVoucherDto_UpdateAndReturnVoucherDto(VoucherDto voucherDto) {
        voucherController.registerVoucher(voucherDto);
        var newVoucherDto = new VoucherDto(
                voucherDto.voucherId(),
                voucherDto.voucherType(),
                new DiscountValue(voucherDto.voucherType(), 1),
                UUID.randomUUID()
        );
        voucherController.updateVoucher(newVoucherDto);
        var updatedVoucherDto = voucherController.getVoucherById(voucherDto.voucherId());
        assertThat(updatedVoucherDto, samePropertyValuesAs(voucherDto));
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 바우처를 갱신 시 실패한다.")
    @MethodSource("provideVoucherDto")
    void updateVoucher_ParamNotExistVoucherDto_Exception(VoucherDto voucherDto) {
        assertThrows(InvalidDataException.class, () -> voucherController.updateVoucher(voucherDto));
    }

    @Test
    @DisplayName("모든 바우처 Dto를 리스트로 반환한다.")
    void getAllVouchers_ParamVoid_ReturnVoucherDtos() {
        voucherController.registerVoucher(voucherDto.get(0));
        var voucherDtos = voucherController.voucherList();
        assertThat(voucherDtos.isEmpty(), is(false));
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 아이디로 찾으면 성공한다.")
    @MethodSource("provideVoucherDto")
    void findVoucherById_ParamExistVoucherDto_ReturnVoucherDto(VoucherDto voucherDto) {
        voucherController.registerVoucher(voucherDto);
        var foundVoucherDto = voucherController.getVoucherById(voucherDto.voucherId());
        assertThat(foundVoucherDto, samePropertyValuesAs(voucherDto));
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 바우처를 아이디로 찾으면 실패한다.")
    @MethodSource("provideVoucherDto")
    void findVoucherById_ParamNotExistVoucherDto_Exception(VoucherDto voucherDto) {
        assertThrows(InvalidDataException.class, () -> voucherController.getVoucherById(voucherDto.voucherId()));
    }

    @Test
    @DisplayName("모든 바우처를 제거한다.")
    void deleteVouchers_ParamVoid_DeleteVoucher() {
        voucherController.unregisterVouchers();
        var voucherDtos = voucherController.voucherList();
        assertThat(voucherDtos.isEmpty(), is(true));
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 제거하면 성공한다.")
    @MethodSource("provideVoucherDto")
    void deleteVoucherById_ParamExistVoucherDto_DeleteVoucher(VoucherDto voucherDto) {
        voucherController.registerVoucher(voucherDto);
        var deletedVoucherDto = voucherController.unregisterVoucherById(voucherDto.voucherId());
        assertThat(deletedVoucherDto, samePropertyValuesAs(voucherDto));
        assertThrows(InvalidDataException.class, () -> voucherController.getVoucherById(voucherDto.voucherId()));
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 바우처를 제거하면 실패한다.")
    @MethodSource("provideVoucherDto")
    void deleteVoucherById_ParamNotExistVoucherDto_Exception(VoucherDto voucherDto) {
        assertThrows(InvalidDataException.class, () -> voucherController.unregisterVoucherById(voucherDto.voucherId()));
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 고객, 바우처 아이디로 제거 시 성공한다.")
    @MethodSource("provideVoucherDto")
    void unregisterVoucherByCustomerIdAndVoucherId_ParamExistIds_DeleteVoucher(VoucherDto voucherDto) {
        voucherController.registerVoucher(voucherDto);
        var deleted = voucherController.unregisterVoucherByCustomerIdAndVoucherId(voucherDto.customerId(), voucherDto.voucherId());
        assertThat(deleted, samePropertyValuesAs(voucherDto));
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 바우처를 고객, 바우처 아이디로 제거 시 실패한다.")
    @MethodSource("provideVoucherDto")
    void unregisterVoucherByCustomerIdAndVoucherId_ParamNotExistIds_Exception(VoucherDto voucherDto) {
        assertThrows(InvalidDataException.class, () -> voucherController.unregisterVoucherByCustomerIdAndVoucherId(voucherDto.customerId(), voucherDto.voucherId()));
    }

}