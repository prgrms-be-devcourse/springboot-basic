package com.programmers.springmission.voucher.application;

import com.programmers.springmission.voucher.domain.Voucher;
import com.programmers.springmission.voucher.domain.enums.VoucherType;
import com.programmers.springmission.voucher.presentation.request.VoucherCreateRequest;
import com.programmers.springmission.voucher.presentation.response.VoucherResponse;
import com.programmers.springmission.voucher.repository.InMemoryVoucherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherServiceTest {

    InMemoryVoucherRepository repository;
    VoucherService service;

    @BeforeEach
    void beforeEach() {
        repository = new InMemoryVoucherRepository();
        service = new VoucherService(repository);
    }

    @DisplayName("FixedAmountVoucher create 성공 테스트")
    @Test
    void fixed_voucher_create_success() {

        // given
        VoucherCreateRequest voucherCreateRequest = new VoucherCreateRequest(VoucherType.FIXED_AMOUNT, 10L);

        // when
        service.createVoucher(voucherCreateRequest);

        // then
        List<Voucher> all = repository.findAll();
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.get(0).getVoucherPolicy().getAmount()).isEqualTo(10L);
        assertThat(all.get(0).getVoucherType()).isEqualTo(VoucherType.FIXED_AMOUNT);
    }

    @DisplayName("PercentDiscountVoucher create 성공 테스트")
    @Test
    void percent_voucher_create_success() {

        // given
        VoucherCreateRequest voucherCreateRequest = new VoucherCreateRequest(VoucherType.PERCENT_DISCOUNT, 10L);

        // when
        service.createVoucher(voucherCreateRequest);

        // then
        List<Voucher> all = repository.findAll();
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.get(0).getVoucherPolicy().getAmount()).isEqualTo(10L);
        assertThat(all.get(0).getVoucherType()).isEqualTo(VoucherType.PERCENT_DISCOUNT);
    }

    @DisplayName("findAllVoucher 바우처 전체 조회 성공 테스트")
    @Test
    void find_voucher_all_success() {

        // given
        VoucherCreateRequest voucherCreateRequest1 = new VoucherCreateRequest(VoucherType.FIXED_AMOUNT, 10L);
        VoucherCreateRequest voucherCreateRequest2 = new VoucherCreateRequest(VoucherType.PERCENT_DISCOUNT, 10L);

        // when
        service.createVoucher(voucherCreateRequest1);
        service.createVoucher(voucherCreateRequest2);

        // then
        List<VoucherResponse> voucherResponses = service.findAllVoucher();
        assertThat(voucherResponses.size()).isEqualTo(2);
    }
}

