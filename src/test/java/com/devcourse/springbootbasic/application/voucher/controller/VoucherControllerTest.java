package com.devcourse.springbootbasic.application.voucher.controller;

import com.devcourse.springbootbasic.application.customer.model.Customer;
import com.devcourse.springbootbasic.application.voucher.model.DiscountValue;
import com.devcourse.springbootbasic.application.voucher.model.Voucher;
import com.devcourse.springbootbasic.application.voucher.model.VoucherType;
import com.devcourse.springbootbasic.application.voucher.service.VoucherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SpringJUnitConfig
class VoucherControllerTest {

    VoucherController controller;

    VoucherService service;

    @BeforeEach
    void init() {
        service = mock(VoucherService.class);
        controller = new VoucherController(service);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 바우처를 생성 시 성공한다.")
    @MethodSource("provideVoucherDto")
    void registerVoucher_ParamNotExistVoucherDto_RegisterAndReturnVoucherDto(VoucherDto voucherDto) {
        given(service.createVoucher(any())).willReturn(VoucherDto.to(voucherDto));

        VoucherDto createdVoucher = controller.registerVoucher(voucherDto);

        assertThat(createdVoucher.voucherId()).isEqualTo(voucherDto.voucherId());
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 갱신 시 성공한다.")
    @MethodSource("provideVoucherDto")
    void updateVoucher_ParamExistVoucherDto_UpdateAndReturnVoucherDto(VoucherDto voucherDto) {
        given(service.updateVoucher(any())).willReturn(VoucherDto.to(voucherDto));

        VoucherDto updatedVoucher = controller.updateVoucher(voucherDto);

        assertThat(updatedVoucher.voucherId()).isEqualTo(voucherDto.voucherId());
    }

    @Test
    @DisplayName("모든 바우처 디티오를 리스트로 반환한다.")
    void voucherList_ParamVoid_ReturnVoucherList() {
        given(service.findVouchers()).willReturn(vouchers);

        List<VoucherDto> result = controller.voucherList();

        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 아이디로 찾으면 성공한다.")
    @MethodSource("provideVoucherDto")
    void getVoucherById_ParamExistVoucherId_ReturnVoucherDto(VoucherDto voucherDto) {
        given(service.findVoucherById(any())).willReturn(VoucherDto.to(voucherDto));

        VoucherDto foundVoucherDto = controller.getVoucherById(voucherDto.voucherId());

        assertThat(foundVoucherDto.voucherId()).isEqualTo(voucherDto.voucherId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 바우처를 아이디로 찾으면 실패한다.")
    @MethodSource("provideVoucherDto")
    void getVoucherId_ParamNotExistVoucherId_Exception(VoucherDto voucherDto) {
        Exception exception = catchException(() -> controller.getVoucherById(voucherDto.voucherId()));

        assertThat(exception).isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 제거하면 성공한다.")
    @MethodSource("provideVoucherDto")
    void unregisterVoucherById_ParamExistVoucherId_DeleteAndReturnVoucherDto(VoucherDto voucherDto) {
        given(service.deleteVoucherById(any())).willReturn(VoucherDto.to(voucherDto));

        VoucherDto deletedVoucherDto = controller.unregisterVoucherById(voucherDto.voucherId());

        assertThat(deletedVoucherDto.voucherId()).isEqualTo(voucherDto.voucherId());
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 고객, 바우처 아이디로 제거 시 성공한다.")
    @MethodSource("provideVoucherDto")
    void unregisterVoucherByCustomerIdAndVoucherId_ParamIds_ReturnVoucherDto(VoucherDto voucherDto) {
        given(service.deleteVoucherCustomerByCustomerIdAndVoucherId(any(), any())).willReturn(VoucherDto.to(voucherDto));

        VoucherDto deleted = controller.unregisterVoucherByCustomerIdAndVoucherId(voucherDto.customerId(), voucherDto.voucherId());

        assertThat(deleted.voucherId()).isEqualTo(voucherDto.voucherId());
    }

    static List<Customer> customers = List.of(
            new Customer(UUID.randomUUID(), "사과", false),
            new Customer(UUID.randomUUID(), "딸기", true)
    );

    static List<VoucherDto> voucherDto = List.of(
            new VoucherDto(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 23), customers.get(0).getCustomerId()),
            new VoucherDto(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, 41), customers.get(0).getCustomerId()),
            new VoucherDto(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 711), customers.get(0).getCustomerId())
    );

    static List<Voucher> vouchers = voucherDto.stream()
            .map(VoucherDto::to)
            .toList();

    static Stream<Arguments> provideVoucherDto() {
        return voucherDto.stream()
                .map(Arguments::of);
    }

}