package com.programmers.vouchermanagement.service;

import com.programmers.vouchermanagement.domain.voucher.FixedAmountVoucher;
import com.programmers.vouchermanagement.domain.voucher.PercentDiscountVoucher;
import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.domain.voucher.VoucherType;
import com.programmers.vouchermanagement.dto.voucher.CreateVoucherRequestDto;
import com.programmers.vouchermanagement.repository.voucher.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;

@SpringBootTest
class VoucherServiceTest {

    @MockBean
    private VoucherRepository voucherRepository;

    @Autowired
    private VoucherService voucherService;

    @Test
    @DisplayName("FIXED_AMOUNT 바우처를 생성할 수 있다.")
    void createFixedAmountVoucher() {
        // given
        CreateVoucherRequestDto request = new CreateVoucherRequestDto();
        request.setVoucherType(VoucherType.FIXED_AMOUNT);
        request.setAmount(1000L);

        // when
        voucherService.createVoucher(request);

        // then
        verify(voucherRepository).save(any(FixedAmountVoucher.class));

    }

    @Test
    @DisplayName("PERCENT_DISCOUNT 바우처를 생성할 수 있다.")
    void createPercentDiscountVoucher() {
        // given
        CreateVoucherRequestDto request = new CreateVoucherRequestDto();
        request.setVoucherType(VoucherType.PERCENT_DISCOUNT);
        request.setAmount(10L);

        // when
        voucherService.createVoucher(request);

        // then
        verify(voucherRepository).save(any(PercentDiscountVoucher.class));
    }

    @Test
    @DisplayName("바우처 목록을 조회할 수 있다.")
    void getVouchers() {
        // given
        List<Voucher> mockVouchers = Arrays.asList(
                FixedAmountVoucher.fixture(),
                PercentDiscountVoucher.fixture());
        given(voucherRepository.findAll()).willReturn(mockVouchers);

        // when
        List<Voucher> resultVouchers = voucherService.getVouchers();

        // then
        assertThat(resultVouchers).hasSize(2);
        assertThat(resultVouchers).extracting(Voucher::getType)
                .containsExactlyInAnyOrder(VoucherType.FIXED_AMOUNT, VoucherType.PERCENT_DISCOUNT);
        assertThat(resultVouchers).extracting(Voucher::getAmount)
                .containsExactlyInAnyOrder(1000L, 10L);
    }
}
