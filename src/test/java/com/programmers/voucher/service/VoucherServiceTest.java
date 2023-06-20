package com.programmers.voucher.service;

import com.programmers.voucher.repository.VoucherRepository;
import com.programmers.voucher.request.VoucherCreateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

    @Mock
    VoucherRepository voucherRepository;

    @InjectMocks
    VoucherService voucherService;

    @Test
    void createVoucherButInvalidType_Then_Exception() {
        //given
        VoucherCreateRequest request = new VoucherCreateRequest("invalid", 10);

        //when

        //then
        assertThatThrownBy(() -> voucherService.createVoucher(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createFixedAmountVoucher() {
        //given
        VoucherCreateRequest request = new VoucherCreateRequest("fixed", 10);

        //when
        voucherService.createVoucher(request);

        //then
    }

    @Test
    void createPercentDiscountVoucher() {
        //given
        VoucherCreateRequest request = new VoucherCreateRequest("percent", 10);

        //when
        voucherService.createVoucher(request);

        //then
    }


}