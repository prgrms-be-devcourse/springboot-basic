package com.programmers.voucher.domain.voucher.controller;

import com.programmers.voucher.domain.voucher.domain.VoucherType;
import com.programmers.voucher.domain.voucher.dto.VoucherDto;
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

import static com.programmers.voucher.testutil.VoucherTestUtil.createFixedVoucherDto;
import static com.programmers.voucher.testutil.VoucherTestUtil.createPercentVoucherDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class VoucherConsoleControllerTest {

    @InjectMocks
    private VoucherConsoleController voucherController;

    @Mock
    private Console console;

    @Mock
    private VoucherService voucherService;

    @Test
    @DisplayName("성공: voucher 단건 생성 요청")
    void createVoucher() {
        //given
        VoucherCreateRequest request = new VoucherCreateRequest(VoucherType.FIXED_AMOUNT, 10);

        given(voucherService.createVoucher(any(), anyLong())).willReturn(UUID.randomUUID());

        //when
        voucherController.createVoucher(request);

        //then
        then(voucherService).should().createVoucher(any(), anyLong());

    }

    @Test
    @DisplayName("성공: voucher 목록 조회 요청")
    void findVouchers() {
        //given
        VoucherDto fixedVoucher = createFixedVoucherDto(UUID.randomUUID(), 10);
        VoucherDto percentVoucher = createPercentVoucherDto(UUID.randomUUID(), 10);
        List<VoucherDto> vouchers = List.of(fixedVoucher, percentVoucher);

        given(voucherService.findVouchers()).willReturn(vouchers);

        //when
        List<VoucherDto> result = voucherController.findVouchers();

        //then
        assertThat(result).usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(fixedVoucher, percentVoucher);
    }

    @Test
    @DisplayName("성공: voucher 단건 삭제 요청")
    void deleteVoucher() {
        //given
        UUID voucherId = UUID.randomUUID();

        //when
        voucherController.deleteVoucher(voucherId);

        //then
        then(voucherService).should().deleteVoucher(any());
    }
}