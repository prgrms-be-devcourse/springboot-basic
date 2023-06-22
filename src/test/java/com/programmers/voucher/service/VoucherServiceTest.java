package com.programmers.voucher.service;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.enumtype.VoucherType;
import com.programmers.voucher.repository.VoucherRepository;
import com.programmers.voucher.request.VoucherCreateRequest;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

    @Mock
    VoucherRepository voucherRepository;

    @InjectMocks
    VoucherService voucherService;

    @Test
    void createFixedAmountVoucher() {
        //given
        VoucherCreateRequest request = new VoucherCreateRequest(VoucherType.FIXED_AMOUNT, 10);

        //when
        voucherService.createVoucher(request);

        //then
    }

    @Test
    void createPercentDiscountVoucher() {
        //given
        VoucherCreateRequest request = new VoucherCreateRequest(VoucherType.PERCENT, 10);

        //when
        voucherService.createVoucher(request);

        //then
    }

    @Test
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