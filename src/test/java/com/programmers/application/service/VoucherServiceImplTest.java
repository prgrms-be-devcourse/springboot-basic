package com.programmers.application.service;

import com.programmers.application.domain.voucher.VoucherType;
import com.programmers.application.dto.reponse.VoucherInfoResponse;
import com.programmers.application.dto.request.RequestFactory;
import com.programmers.application.dto.request.VoucherCreationRequest;
import com.programmers.application.repository.MemoryVoucherRepository;
import com.programmers.application.repository.VoucherRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class VoucherServiceImplTest {
    private static final int FIXED_DISCOUNT_AMOUNT = 100;
    private static final int PERCENT_DISCOUNT_AMOUNT = 10;
    private static final String FIXED_AMOUNT_VOUCHER_TYPE = "fixed";
    private static final String PERCENT_DISCOUNT_VOUCHER_TYPE = "percent";

    private VoucherRepository voucherRepository = new MemoryVoucherRepository();
    private VoucherService voucherService = new VoucherServiceImpl(voucherRepository);

    @DisplayName("다양한 바우처 생성 성공 테스트")
    @ParameterizedTest
    @CsvSource(value = {
            "FIXED, 100",
            "PERCENT, 10"})
    void createVoucher(String voucherType, long discountAmount) {
        //give
        VoucherCreationRequest voucherCreationRequest = RequestFactory.createVoucherCreationRequest(voucherType, discountAmount);

        //when
        UUID voucherId = voucherService.createVoucher(voucherCreationRequest);

        //then
        Assertions.assertThat(voucherRepository.findByVoucherId(voucherId)).isPresent();

    }

    @DisplayName("잘못된 바우처 타입 입력시 예외 발생 테스트")
    @ParameterizedTest
    @CsvSource(value = {
            "wrongType, 100",
            "weirdType, 10"})
    void inputIncorrectVoucherType(String voucherType, long discountAmount) {
        VoucherCreationRequest voucherCreationRequest = RequestFactory.createVoucherCreationRequest(voucherType, discountAmount);
        Assertions.assertThatThrownBy(() -> voucherService.createVoucher(voucherCreationRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("바우처 타입을 입력하지 않을시 예외 발생 테스트")
    @ParameterizedTest
    @NullAndEmptySource
    void inputNullVoucherType(String voucherType) {
        Assertions.assertThatThrownBy(() -> RequestFactory.createVoucherCreationRequest(voucherType, PERCENT_DISCOUNT_AMOUNT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("바우처 타입과 할인양을 입력해주세요.");
    }

    @DisplayName("바우처 할인양이 0일 경우 예외 발생 테스트")
    @ParameterizedTest
    @EnumSource(VoucherType.class)
    void input0VoucherDiscountAmount(VoucherType voucherType) {
        final long discountAmount = 0;
        String requestVoucherType = voucherType.name();
        Assertions.assertThatThrownBy(() -> RequestFactory.createVoucherCreationRequest(requestVoucherType , discountAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("바우처 타입과 할인양을 입력해주세요.");
    }

    @DisplayName("바우처 목록 조회 성공 테스트")
    @Test
    void findVoucherList() {
        final int expectedCount = 2;
        //give
        VoucherCreationRequest voucherCreationRequest1 = RequestFactory.createVoucherCreationRequest(FIXED_AMOUNT_VOUCHER_TYPE, FIXED_DISCOUNT_AMOUNT);
        VoucherCreationRequest voucherCreationRequest2 = RequestFactory.createVoucherCreationRequest(PERCENT_DISCOUNT_VOUCHER_TYPE, PERCENT_DISCOUNT_AMOUNT);
        ArrayList<VoucherCreationRequest> voucherCreationRequests = new ArrayList<>(List.of(voucherCreationRequest1, voucherCreationRequest2));
        createAndSaveVoucher(voucherCreationRequests);

        //when
        List<VoucherInfoResponse> voucherList = voucherService.findVoucherList();

        //then
        Assertions.assertThat(voucherList).hasSize(expectedCount);
    }

    private void createAndSaveVoucher(List<VoucherCreationRequest> voucherCreationRequestList) {
        voucherCreationRequestList
                .stream()
                .forEach(voucherService::createVoucher);
    }
}
