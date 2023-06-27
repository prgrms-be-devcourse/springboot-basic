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

import java.util.List;

class MemoryVoucherRepositoryTest {
    private static final int PERCENT_DISCOUNT_AMOUNT = 10;
    private static final int FIXED_DISCOUNT_AMOUNT = 100;
    private static final String FIXED_AMOUNT_VOUCHER_TYPE = "fixed";
    private static final String PERCENT_DISCOUNT_VOUCHER_TYPE = "percent";
    private static final int EXPECTED_COUNT = 2;

    private VoucherRepository voucherRepository = new MemoryVoucherRepository();

    @DisplayName("바우처 저장 성공 테스트")
    @ParameterizedTest
    @CsvSource(value = {
            "fixed, 100",
            "percent, 10"})
    void save(String voucherType, long discountAmount) {
        //give
        VoucherCreationRequest voucherCreationRequest = RequestFactory.createVoucherCreationRequest(voucherType, discountAmount);
        Voucher voucher = VoucherFactory.createVoucher(voucherCreationRequest);

        //when
        voucherRepository.save(voucher);

        //then
        Voucher savedVoucher = voucherRepository.findByVoucherId(voucher.getVoucherId()).get();
        Assertions.assertThat(voucher).isEqualTo(savedVoucher);
    }

    @DisplayName("바우처 목록 조회 성공 테스트")
    @Test
    void findAll() {
        //give
        VoucherCreationRequest voucherCreationRequest1 = RequestFactory.createVoucherCreationRequest(FIXED_AMOUNT_VOUCHER_TYPE, FIXED_DISCOUNT_AMOUNT);
        VoucherCreationRequest voucherCreationRequest2 = RequestFactory.createVoucherCreationRequest(PERCENT_DISCOUNT_VOUCHER_TYPE, PERCENT_DISCOUNT_AMOUNT);

        Voucher voucher1 = VoucherFactory.createVoucher(voucherCreationRequest1);
        Voucher voucher2 = VoucherFactory.createVoucher(voucherCreationRequest2);

        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);

        //when
        List<Voucher> voucherList = voucherRepository.findAll();

        //then
        Assertions.assertThat(voucherList.size()).isEqualTo(EXPECTED_COUNT);
    }
}
