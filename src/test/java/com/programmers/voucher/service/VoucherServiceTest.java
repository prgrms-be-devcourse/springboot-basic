package com.programmers.voucher.service;

import com.programmers.voucher.domain.VoucherType;
import com.programmers.voucher.dto.reponse.VoucherInfoResponse;
import com.programmers.voucher.dto.request.VoucherCreationRequest;
import com.programmers.voucher.repository.MemoryVoucherRepository;
import com.programmers.voucher.repository.VoucherRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;
import java.util.UUID;

class VoucherServiceTest {
    private static final int FIXED_DISCOUNT_AMOUNT = 100;
    private static final int PERCENT_DISCOUNT_AMOUNT = 10;
    private static final String FIXED_AMOUNT_VOUCHER_TYPE = "FIXED";
    private static final String PERCENT_DISCOUNT_VOUCHER_TYPE = "PERCENT";
    private static final int EXPECTED_COUNT = 2;

    private VoucherRepository voucherRepository = new MemoryVoucherRepository();
    private VoucherService voucherService = new VoucherService(voucherRepository);

    @DisplayName("다양한 바우처 생성 성공 테스트")
    @ParameterizedTest
    @CsvSource(value = {
            "FIXED, 100",
            "PERCENT, 10"})
    void createVoucher(String voucherType, long discountAmount) {
        //give
        VoucherCreationRequest voucherCreationRequest = new VoucherCreationRequest(voucherType, discountAmount);

        //when
        UUID voucherId = voucherService.createVoucher(voucherCreationRequest);

        //then
        Assertions.assertThat(voucherRepository.findByVoucherId(voucherId).isPresent()).isTrue();

    }

    @DisplayName("잘못된 바우처 타입 입력시 예외 발생 테스트")
    @ParameterizedTest
    @CsvSource(value = {
            "wrongType, 100",
            "weirdType, 10"})
    void inputIncorrectVoucherType(String voucherType, long discountAmount) {
        VoucherCreationRequest voucherCreationRequest = new VoucherCreationRequest(voucherType, discountAmount);
        Assertions.assertThatThrownBy(() -> voucherService.createVoucher(voucherCreationRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("바우처 타입을 입력하지 않을시 예외 발생 테스트")
    @ParameterizedTest
    @NullAndEmptySource
    void inputNullVoucherType(String voucherType) {
        Assertions.assertThatThrownBy(() -> new VoucherCreationRequest(voucherType, PERCENT_DISCOUNT_AMOUNT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("바우처 타입과 할인양을 입력해주세요.");
    }

    @DisplayName("바우처 할인양이 0일경우 예외 발생 테스트")
    @ParameterizedTest
    @EnumSource(VoucherType.class)
    void input0VoucherDiscountAmount(VoucherType voucherType) {
        final long discountAmount = 0;
        String requestVoucherType = voucherType.name();
        Assertions.assertThatThrownBy(() -> new VoucherCreationRequest(requestVoucherType , discountAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("바우처 타입과 할인양을 입력해주세요.");
    }

    @DisplayName("바우처 목록 조회 성공 테스트")
    @Test
    void findVoucherList() {
        //give
        VoucherCreationRequest voucherCreationRequest1 = new VoucherCreationRequest(FIXED_AMOUNT_VOUCHER_TYPE, FIXED_DISCOUNT_AMOUNT);
        VoucherCreationRequest voucherCreationRequest2 = new VoucherCreationRequest(PERCENT_DISCOUNT_VOUCHER_TYPE, PERCENT_DISCOUNT_AMOUNT);
        voucherService.createVoucher(voucherCreationRequest1);
        voucherService.createVoucher(voucherCreationRequest2);

        //when
        List<VoucherInfoResponse> voucherList = voucherService.findVoucherList();

        //then
        Assertions.assertThat(voucherList.size()).isEqualTo(EXPECTED_COUNT);
    }
}
