package com.programmers.vouchermanagement.voucher.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponse;
import com.programmers.vouchermanagement.voucher.repository.VoucherRepository;

class VoucherServiceTest {
    static VoucherRepository voucherRepository;
    static VoucherService voucherService;

    @BeforeAll
    static void setUp() {
        voucherRepository = mock();
        voucherService = new VoucherService(voucherRepository);
    }
    
    @Test
    @DisplayName("고정 금액 바우처 생성에 성공한다.")
    void testFixedVoucherCreationSuccessful() {
        //given
        CreateVoucherRequest request = new CreateVoucherRequest(new BigDecimal("100"), VoucherType.FIXED);

        //when
        VoucherResponse voucherResponse = voucherService.create(request);

        //then
        assertThat(voucherResponse.isPercentVoucher(), is(false));
    }

    @Test
    @DisplayName("유효하지 않은 할인 값의 고정 금액 바우처 생성에 실패한다.")
    void testFixedVoucherCreationFailed_InvalidAmount() {
        //given
        CreateVoucherRequest request = new CreateVoucherRequest(new BigDecimal("0"), VoucherType.FIXED);

        //when & then
        assertThatThrownBy(() -> voucherService.create(request)).isInstanceOf(IllegalArgumentException.class);
    }
}
