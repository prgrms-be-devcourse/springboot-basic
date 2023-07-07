package com.programmers.vouchermanagement.voucher.application;

import com.programmers.vouchermanagement.voucher.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
    @DisplayName("바우처를 생성한다.")
    void createVoucher() {
        // given
        VoucherDto request = new VoucherDto(DiscountType.FIX, 5000);
        DiscountType discountType = request.discountType();
        int amount = request.amount();
        DiscountPolicy discountPolicy = discountType.createDiscountPolicy(amount);
        Voucher voucher = new Voucher(discountPolicy);

        given(voucherRepository.save(any(Voucher.class)))
                .willReturn(voucher);

        // when
        Voucher result = voucherService.createVoucher(request);

        // then
        assertThat(result).isNotNull();

        // verify
        verify(voucherRepository, times(1)).save(any(Voucher.class));
    }

    @Test
    @DisplayName("바우처를 모두 조회한다.")
    void getVouchers() {
        // given
        Voucher voucher1 = new Voucher(new FixedAmountDiscountPolicy(5000));
        Voucher voucher2 = new Voucher(new PercentDiscountPolicy(10));

        given(voucherRepository.findAll())
                .willReturn(List.of(voucher1, voucher2));

        // when
        List<VoucherDto> result = voucherService.getVouchers();

        // then
        assertThat(result).hasSize(2);

        // verify
        verify(voucherRepository, times(1)).findAll();
    }
}