package com.programmers.voucher.service;

import com.programmers.voucher.domain.voucher.domain.Voucher;
import com.programmers.voucher.domain.voucher.service.VoucherService;
import com.programmers.voucher.domain.voucher.domain.VoucherType;
import com.programmers.voucher.domain.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static com.programmers.voucher.testutil.VoucherTestUtil.createFixedVoucher;
import static com.programmers.voucher.testutil.VoucherTestUtil.createPercentVoucher;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

    @Mock
    VoucherRepository voucherRepository;

    @InjectMocks
    VoucherService voucherService;

    @Test
    @DisplayName("FixedAmountVoucher 생성 - 성공")
    void createFixedAmountVoucher() {
        //given
        VoucherType voucherType = VoucherType.FIXED_AMOUNT;
        long amount = 10;

        //when
        voucherService.createVoucher(voucherType, amount);

        //then
        then(voucherRepository).should().save(any());
    }

    @Test
    @DisplayName("PercentDiscountVoucher 생성 - 성공")
    void createPercentDiscountVoucher() {
        //given
        VoucherType voucherType = VoucherType.PERCENT;
        long percent = 10;

        //when
        voucherService.createVoucher(voucherType, percent);

        //then
        then(voucherRepository).should().save(any());
    }

    @Test
    @DisplayName("Voucher 목록 조회 - 성공")
    void findVouchers() {
        //given
        Voucher fixedVoucher = createFixedVoucher(UUID.randomUUID(), 10);
        Voucher percentVoucher = createPercentVoucher(UUID.randomUUID(), 10);

        List<Voucher> store = List.of(fixedVoucher, percentVoucher);

        given(voucherRepository.findAll()).willReturn(store);

        //when
        List<Voucher> result = voucherService.findVouchers();

        //then
        assertThat(result).contains(fixedVoucher, percentVoucher);
    }

}