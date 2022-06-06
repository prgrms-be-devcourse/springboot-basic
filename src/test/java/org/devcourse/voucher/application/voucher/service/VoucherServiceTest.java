package org.devcourse.voucher.application.voucher.service;

import org.devcourse.voucher.application.voucher.controller.dto.VoucherRequest;
import org.devcourse.voucher.application.voucher.controller.dto.VoucherResponse;
import org.devcourse.voucher.application.voucher.model.Voucher;
import org.devcourse.voucher.application.voucher.model.VoucherType;
import org.devcourse.voucher.application.voucher.repository.VoucherRepository;
import org.devcourse.voucher.core.exception.NotFoundException;
import org.devcourse.voucher.core.exception.stub.VoucherStubs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.devcourse.voucher.application.voucher.model.VoucherType.FIXED_AMOUNT_VOUCHER;
import static org.devcourse.voucher.application.voucher.model.VoucherType.PERCENT_DISCOUNT_VOUCHER;
import static org.devcourse.voucher.core.exception.ErrorType.NOT_FOUND_VOUCHER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

    @Spy
    @InjectMocks
    VoucherService voucherService;

    @Mock
    VoucherRepository voucherRepository;

    private final UUID voucherId = UUID.randomUUID();

    private final Pageable pageable = Pageable.ofSize(5);

    private static Stream<Arguments> createArguments() {
        return Stream.of(
                Arguments.arguments(FIXED_AMOUNT_VOUCHER, 1000),
                Arguments.arguments(PERCENT_DISCOUNT_VOUCHER, 10)
        );
    }

    @ParameterizedTest
    @MethodSource("createArguments")
    @DisplayName("바우처 생성 테스트")
    void createVoucherTest(VoucherType voucherType, long discount) {
        // given
        Voucher ret = voucherType.voucherCreator(voucherId, discount);
        VoucherResponse want = new VoucherResponse(voucherId, voucherType, discount);

        // when
        doReturn(ret)
                .when(voucherRepository)
                .insert(any(Voucher.class));

        // then
        assertThat(voucherService.createVoucher(voucherType, discount))
                .usingRecursiveComparison()
                .isEqualTo(want);
    }

    @Test
    @DisplayName("페이지를 이용한 바우처 조회 테스트")
    void recallAllVoucherTest() {
        // given
        List<Voucher> ret = VoucherStubs.voucherList();
        List<VoucherResponse> want = VoucherStubs.voucherResponseList(ret);

        // when
        doReturn(ret)
                .when(voucherRepository)
                .findAll(any(Pageable.class));

        // then
        assertThat(voucherService.recallAllVoucher(pageable))
                .usingRecursiveComparison()
                .isEqualTo(want);
    }

    @Test
    @DisplayName("아이디로 바우처 조회 테스트")
    void recallVoucherByIdTest() {
        // given
        Voucher ret = VoucherStubs.fixedAmountVoucher(voucherId);
        VoucherResponse want = VoucherStubs.voucherResponse(voucherId);

        // when
        doReturn(Optional.of(ret))
                .when(voucherRepository)
                .findById(voucherId);

        // then
        assertThat(voucherService.recallVoucherById(voucherId))
                .usingRecursiveComparison()
                .isEqualTo(want);
    }

    @Test
    @DisplayName("존재하지 않는 아이디로 바우처 조회 시도시 예외 발생")
    void notValidRecallVoucherByIdTest() {
        // given
        Voucher notExistVoucher = VoucherStubs.fixedAmountVoucher(voucherId);

        // when
        doThrow(new NotFoundException(NOT_FOUND_VOUCHER, voucherId))
                .when(voucherService)
                .recallVoucherById(voucherId);

        // then
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> voucherService.recallVoucherById(voucherId));
    }

    @Test
    @DisplayName("바우처 업데이트 테스트")
    void updateVoucherTest() {
        // given
        Voucher origin = VoucherStubs.fixedAmountVoucher(voucherId);
        Voucher updated = VoucherStubs.fixedAmountVoucher(voucherId);
        updated.setDiscount(2000);
        VoucherResponse want = VoucherResponse.of(updated);

        // when
        doReturn(Optional.of(origin))
                .when(voucherRepository)
                .findById(any(UUID.class));
        doReturn(updated)
                .when(voucherRepository)
                .update(any(Voucher.class));
        // then
        assertThat(voucherService.updateVoucher(want.getVoucherId(), 2000))
                .usingRecursiveComparison()
                .isEqualTo(want);
    }

    @Test
    @DisplayName("존재하지 않는 바우처 업데이트 시도시 예외 발생")
    void notValidUpdateVoucherTest() {
        // given
        Voucher notExistVoucher = VoucherStubs.fixedAmountVoucher(voucherId);
        long updateDiscount = 1000;

        // when
        doThrow(new NotFoundException(NOT_FOUND_VOUCHER, voucherId))
                .when(voucherService)
                .updateVoucher(voucherId, updateDiscount);

        // then
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> voucherService.updateVoucher(voucherId, updateDiscount));
    }

    @Test
    @DisplayName("바우처 삭제 테스트")
    void deleteVoucherTest() {
        // when
        doNothing()
                .when(voucherRepository)
                .deleteById(voucherId);
        // then
        voucherService.deleteVoucher(voucherId);
        verify(voucherRepository).deleteById(voucherId);
    }
}