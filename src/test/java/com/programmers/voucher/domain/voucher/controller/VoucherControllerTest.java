package com.programmers.voucher.domain.voucher.controller;

import com.programmers.voucher.domain.voucher.domain.Voucher;
import com.programmers.voucher.domain.voucher.domain.VoucherType;
import com.programmers.voucher.domain.voucher.dto.request.VoucherCreateRequest;
import com.programmers.voucher.domain.voucher.service.VoucherService;
import com.programmers.voucher.global.io.Console;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class VoucherControllerTest {

    @InjectMocks
    private VoucherController voucherController;

    @Mock
    private Console console;

    @Mock
    private VoucherService voucherService;

    @Test
    @DisplayName("성공: voucher 단건 생성 요청")
    void createVoucher() {
        //given
        VoucherCreateRequest request = new VoucherCreateRequest(VoucherType.FIXED_AMOUNT, 10);

        given(console.inputVoucherCreateInfo()).willReturn(request);
        given(voucherService.createVoucher(any(), anyLong())).willReturn(UUID.randomUUID());

        //when
        voucherController.createVoucher();

        //then
        then(voucherService).should().createVoucher(any(), anyLong());
        then(console).should().print(any());

    }

    @Test
    @DisplayName("성공: voucher 목록 조회 요청")
    void findVouchers() {
        //given
        Voucher fixedVoucher = createFixedVoucher(UUID.randomUUID(), 10);
        Voucher percentVoucher = createPercentVoucher(UUID.randomUUID(), 10);
        List<Voucher> vouchers = List.of(fixedVoucher, percentVoucher);

        given(voucherService.findVouchers()).willReturn(vouchers);

        //when
        voucherController.findVouchers();

        //then
        then(voucherService).should().findVouchers();
        then(console).should().printVouchers(any());
    }

    @Test
    @DisplayName("성공: voucher 단건 삭제 요청")
    void deleteVoucher() {
        //given
        given(console.inputUUID()).willReturn(UUID.randomUUID());

        //when
        voucherController.deleteVoucher();

        //then
        then(voucherService).should().deleteVoucher(any());
        then(console).should().print(any());
    }
}