package com.programmers.vouchermanagement.voucher.application;

import com.programmers.vouchermanagement.voucher.domain.FixedAmountVoucher;
import com.programmers.vouchermanagement.voucher.domain.PercentDiscountVoucher;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherRepository;
import com.programmers.vouchermanagement.voucher.dto.VoucherDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

    @InjectMocks
    private VoucherService voucherService;

    @Mock
    private VoucherRepository voucherRepository;

    @Test
    @DisplayName("바우처를 모두 조회한다.")
    void getVouchers() {
        // given
        Voucher voucher1 = new FixedAmountVoucher(5000);
        Voucher voucher2 = new PercentDiscountVoucher(10);

        given(voucherRepository.findAll())
                .willReturn(List.of(voucher1, voucher2));

        // when
        VoucherDto.Response result = voucherService.getVouchers();

        // then
        assertThat(result.voucherName()).hasSize(2);

        // verify
        verify(voucherRepository, times(1)).findAll();
    }
}