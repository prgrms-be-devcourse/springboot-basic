package com.tangerine.voucher_system.application.voucher.service;

import com.tangerine.voucher_system.application.global.exception.InvalidDataException;
import com.tangerine.voucher_system.application.voucher.model.DiscountValue;
import com.tangerine.voucher_system.application.voucher.model.VoucherType;
import com.tangerine.voucher_system.application.voucher.repository.JdbcVoucherRepository;
import com.tangerine.voucher_system.application.voucher.service.dto.VoucherParam;
import com.tangerine.voucher_system.application.voucher.service.dto.VoucherResult;
import com.tangerine.voucher_system.application.voucher.service.mapper.VoucherServiceMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

@JdbcTest
@ActiveProfiles("test")
@Import({VoucherService.class, JdbcVoucherRepository.class})
class VoucherServiceTest {

    @Autowired
    VoucherService service;

    @ParameterizedTest
    @DisplayName("존재하지 않은 바우처를 추가하면 성공한다.")
    @MethodSource("provideVoucherParams")
    void createVoucher_ParamNotExistVoucher_InsertAndReturnVoucher(VoucherParam param) {

        service.createVoucher(param);

        VoucherResult createdVoucher = service.findVoucherById(param.voucherId());
        assertThat(createdVoucher.voucherId()).isEqualTo(param.voucherId());
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 추가하면 실패한다.")
    @MethodSource("provideVoucherParams")
    void createVoucher_ParamExistVoucher_Exception(VoucherParam param) {
        service.createVoucher(param);

        Exception exception = catchException(() -> service.createVoucher(param));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 갱신하면 성공한다.")
    @MethodSource("provideVoucherParams")
    void createVoucher_ParamExistVoucher_UpdateAndReturnVoucher(VoucherParam param) {
        service.createVoucher(param);

        VoucherParam newVoucher = new VoucherParam(param.voucherId(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 23.1), LocalDate.now());
        service.updateVoucher(newVoucher);

        VoucherResult updatedVoucher = service.findVoucherById(param.voucherId());
        assertThat(updatedVoucher.voucherId()).isEqualTo(newVoucher.voucherId());
        assertThat(updatedVoucher.voucherId()).isEqualTo(param.voucherId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않은 바우처를 추가하면 실패한다.")
    @MethodSource("provideVoucherParams")
    void createVoucher_ParamNotExistVoucher_Exception(VoucherParam param) {

        Exception exception = catchException(() -> service.updateVoucher(param));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @Test
    @DisplayName("생성된 바우처가 리스트 형태로 반환되면 성공한다.")
    void findVouchers_ParamVoid_ReturnVoucherList() {
        vouchers.forEach(service::createVoucher);

        List<VoucherResult> result = service.findVouchers();

        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 아이디로 검색하는 경우 성공한다.")
    @MethodSource("provideVoucherParams")
    void findVoucherById_ParamExistVoucher_ReturnVoucher(VoucherParam param) {
        service.createVoucher(param);

        VoucherResult foundVoucher = service.findVoucherById(param.voucherId());

        assertThat(foundVoucher.voucherId()).isEqualTo(param.voucherId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 바우처를 아이디로 검색하는 경우 실패한다.")
    @MethodSource("provideVoucherParams")
    void findVoucherById_ParamNotExistVoucher_Exception(VoucherParam param) {

        Exception exception = catchException(() -> service.findVoucherById(param.voucherId()));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 생성일자로 검색하는 경우 성공한다.")
    @MethodSource("provideVoucherParams")
    void findVoucherByCreatedAt_ParamExistVoucher_ReturnVoucher(VoucherParam param) {
        service.createVoucher(param);

        VoucherResult foundVoucher = service.findVoucherByCreatedAt(param.createdAt());

        assertThat(foundVoucher.voucherId()).isEqualTo(param.voucherId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 바우처를 생성일자로 검색하는 경우 실패한다.")
    @MethodSource("provideVoucherParams")
    void findVoucherByCreatedAt_ParamNotExistVoucher_Exception(VoucherParam param) {

        Exception exception = catchException(() -> service.findVoucherByCreatedAt(param.createdAt()));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 아이디로 제거하면 성공한다.")
    @MethodSource("provideVoucherParams")
    void deleteVoucherById_ParamExistVoucherId_DeleteVoucher(VoucherParam param) {
        service.createVoucher(param);

        VoucherResult deletedVoucher = service.deleteVoucherById(param.voucherId());

        assertThat(deletedVoucher.voucherId()).isEqualTo(param.voucherId());
    }

    @ParameterizedTest
    @DisplayName("없는 바우처를 아이디로 제거하면 실패한다.")
    @MethodSource("provideVoucherParams")
    void deleteVoucherById_ParamNotExistVoucherId_Exception(VoucherParam param) {

        Exception exception = catchException(() -> service.deleteVoucherById(param.voucherId()));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    static Stream<Arguments> provideVoucherParams() {
        return vouchers.stream()
                .map(Arguments::of);
    }

    static List<VoucherParam> vouchers = List.of(
            new VoucherParam(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 100), LocalDate.now()),
            new VoucherParam(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, 1), LocalDate.now()),
            new VoucherParam(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 22), LocalDate.now()),
            new VoucherParam(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, 10), LocalDate.now())
    );

}
