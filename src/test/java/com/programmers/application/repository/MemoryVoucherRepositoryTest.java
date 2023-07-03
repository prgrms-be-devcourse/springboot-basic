package com.programmers.application.repository;

import com.programmers.application.domain.voucher.Voucher;
import com.programmers.application.domain.voucher.VoucherFactory;
import com.programmers.application.dto.request.RequestFactory;
import com.programmers.application.dto.request.VoucherCreationRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

class MemoryVoucherRepositoryTest {
    private static final int PERCENT_DISCOUNT_AMOUNT = 10;
    private static final int FIXED_DISCOUNT_AMOUNT = 100;
    private static final String FIXED_AMOUNT_VOUCHER_TYPE = "fixed";
    private static final String PERCENT_DISCOUNT_VOUCHER_TYPE = "percent";

    private VoucherRepository voucherRepository = new MemoryVoucherRepository();

    @DisplayName("옳바른 바우처 타입과 할인양 입력 시, save()를 실행하면 테스트가 성공한다.")
    @ParameterizedTest
    @CsvSource(value = {"fixed, 100", "percent, 10"})
    void save(String voucherType, long discountAmount) {
        //given
        VoucherCreationRequest voucherCreationRequest = RequestFactory.createVoucherCreationRequest(voucherType, discountAmount);
        Voucher voucher = VoucherFactory.createVoucher(voucherCreationRequest);

        //when
        voucherRepository.save(voucher);

        //then
        Voucher savedVoucher = voucherRepository.findByVoucherId(voucher.getVoucherId()).get();
        Assertions.assertThat(voucher).isEqualTo(savedVoucher);
    }

    @DisplayName("바우처 생성 및 저장 시, finalAll() 실행하면 바우처가 조회된다.")
    @Test
    void findAll() {
        final int expectedCount = 2;
        //given
        VoucherCreationRequest voucherCreationRequest1 = RequestFactory.createVoucherCreationRequest(FIXED_AMOUNT_VOUCHER_TYPE, FIXED_DISCOUNT_AMOUNT);
        VoucherCreationRequest voucherCreationRequest2 = RequestFactory.createVoucherCreationRequest(PERCENT_DISCOUNT_VOUCHER_TYPE, PERCENT_DISCOUNT_AMOUNT);
        ArrayList<VoucherCreationRequest> voucherCreationRequests = new ArrayList<>(List.of(voucherCreationRequest1, voucherCreationRequest2));
        createAndSaveVoucher(voucherCreationRequests);

        //when
        List<Voucher> voucherList = voucherRepository.findAll();

        //then
        Assertions.assertThat(voucherList).hasSize(expectedCount);
    }

    private void createAndSaveVoucher(List<VoucherCreationRequest> voucherCreationRequestList) {
        for (VoucherCreationRequest voucherCreationRequest : voucherCreationRequestList) {
            Voucher voucher = VoucherFactory.createVoucher(voucherCreationRequest);
            voucherRepository.save(voucher);
        }
    }
}
