package com.tangerine.voucher_system.application.voucher.controller;

import com.tangerine.voucher_system.application.global.exception.InvalidDataException;
import com.tangerine.voucher_system.application.voucher.controller.api.VoucherRestController;
import com.tangerine.voucher_system.application.voucher.controller.dto.CreateVoucherRequest;
import com.tangerine.voucher_system.application.voucher.controller.dto.UpdateVoucherRequest;
import com.tangerine.voucher_system.application.voucher.controller.dto.VoucherResponse;
import com.tangerine.voucher_system.application.voucher.model.DiscountValue;
import com.tangerine.voucher_system.application.voucher.model.VoucherType;
import com.tangerine.voucher_system.application.voucher.service.VoucherService;
import com.tangerine.voucher_system.application.voucher.service.dto.VoucherResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SpringJUnitConfig
class VoucherRestControllerTest {

    @InjectMocks
    VoucherRestController controller;

    @Mock
    VoucherService service;

    @BeforeEach
    void init() {
        service = mock(VoucherService.class);
        controller = new VoucherRestController(service);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 바우처를 생성 시 성공한다.")
    @MethodSource("provideCreateVoucherRequests")
    void createVoucher_ParamNotExistVoucherDto_RegisterAndReturnVoucherDto(CreateVoucherRequest request, VoucherResult result) {
        given(service.createVoucher(any())).willReturn(result);

        ResponseEntity<VoucherResponse> createdVoucher = controller.createVoucher(request);

        assertThat(createdVoucher.getBody()).isNotNull();
        assertThat(createdVoucher.getBody().voucherId()).isEqualTo(result.voucherId());
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 갱신 시 성공한다.")
    @MethodSource("provideUpdateVoucherRequests")
    void updateVoucher_ParamExistVoucherDto_UpdateAndReturnVoucherDto(UpdateVoucherRequest request, VoucherResult result) {
        given(service.updateVoucher(any())).willReturn(result);

        ResponseEntity<VoucherResponse> updatedVoucher = controller.updateVoucher(request);

        assertThat(updatedVoucher.getBody()).isNotNull();
        assertThat(updatedVoucher.getBody().voucherId()).isEqualTo(result.voucherId());
    }

    @Test
    @DisplayName("모든 바우처 Dto 를 리스트로 반환한다.")
    void voucherList_ParamVoid_ReturnVoucherList() {
        given(service.findVouchers()).willReturn(voucherResults);

        ResponseEntity<List<VoucherResponse>> result = controller.voucherList();

        assertThat(result.getBody()).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 아이디로 찾으면 성공한다.")
    @MethodSource("provideVoucherResults")
    void voucherById_ParamExistVoucherId_ReturnVoucherDto(VoucherResult result) {
        given(service.findVoucherById(any())).willReturn(result);

        ResponseEntity<VoucherResponse> foundVoucherDto = controller.voucherById(result.voucherId());

        assertThat(foundVoucherDto.getBody()).isNotNull();
        assertThat(foundVoucherDto.getBody().voucherId()).isEqualTo(result.voucherId());
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 생성일자로 찾으면 성공한다.")
    @MethodSource("provideVoucherResults")
    void voucherByCreatedAt_ParamExistVoucherId_ReturnVoucherDto(VoucherResult result) {
        given(service.findVoucherByCreatedAt(any())).willReturn(result);

        ResponseEntity<VoucherResponse> foundVoucherDto = controller.voucherByCreatedAt(result.createdAt());

        assertThat(foundVoucherDto.getBody()).isNotNull();
        assertThat(foundVoucherDto.getBody().voucherId()).isEqualTo(result.voucherId());
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 제거하면 성공한다.")
    @MethodSource("provideVoucherResults")
    void deleteVoucherById_ParamExistVoucherId_DeleteAndReturnVoucherDto(VoucherResult result) {
        given(service.deleteVoucherById(any())).willReturn(result);

        ResponseEntity<VoucherResponse> deletedVoucherDto = controller.deleteVoucherById(result.voucherId());

        assertThat(deletedVoucherDto.getBody()).isNotNull();
        assertThat(deletedVoucherDto.getBody().voucherId()).isEqualTo(result.voucherId());
    }

    static List<VoucherResult> voucherResults = List.of(
            new VoucherResult(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 23), LocalDate.now()),
            new VoucherResult(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, 41), LocalDate.now()),
            new VoucherResult(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 711), LocalDate.now())
    );

    static List<CreateVoucherRequest> createVoucherRequests = voucherResults.stream()
            .map(result -> new CreateVoucherRequest(result.voucherType(), result.discountValue(), result.createdAt()))
            .toList();

    static List<UpdateVoucherRequest> updateVoucherRequests = voucherResults.stream()
            .map(result -> new UpdateVoucherRequest(result.voucherId(), result.voucherType(), result.discountValue(), result.createdAt()))
            .toList();

    static Stream<Arguments> provideCreateVoucherRequests() {
        return IntStream.range(0, voucherResults.size())
                .mapToObj(i -> Arguments.of(createVoucherRequests.get(i), voucherResults.get(i)));
    }

    static Stream<Arguments> provideUpdateVoucherRequests() {
        return IntStream.range(0, voucherResults.size())
                .mapToObj(i -> Arguments.of(updateVoucherRequests.get(i), voucherResults.get(i)));
    }

    static Stream<Arguments> provideVoucherResults() {
        return voucherResults.stream()
                .map(Arguments::of);
    }

}