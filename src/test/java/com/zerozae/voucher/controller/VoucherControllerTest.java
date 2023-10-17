package com.zerozae.voucher.controller;

import com.zerozae.voucher.common.message.MessageConverter;
import com.zerozae.voucher.common.response.Response;
import com.zerozae.voucher.controller.voucher.VoucherController;
import com.zerozae.voucher.domain.voucher.VoucherType;
import com.zerozae.voucher.dto.voucher.VoucherRequest;
import com.zerozae.voucher.service.voucher.VoucherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class VoucherControllerTest {
    VoucherController voucherController = new VoucherController(mock(VoucherService.class));
    MessageConverter messageConverter = new MessageConverter(mock(MessageSource.class));

    @Test
    @DisplayName("바우처 컨트롤러 : 바우처 생성 메서드 성공 반환 테스트")
    void createVoucherSuccessTest(){
        // Given
        VoucherRequest voucherRequest = new VoucherRequest(10, VoucherType.FIXED);

        // When
        Response response = voucherController.createVoucher(voucherRequest);

        // Then
        assertTrue(response.isSuccess());
    }

    @Test
    @DisplayName("바우처 컨트롤러 : 바우처 생성 메서드 실패 테스트 (음수 값 입력)")
    void createVoucherFailedTest(){
        // Given
        VoucherRequest voucherRequest = new VoucherRequest(-5, VoucherType.FIXED);

        // When
        Response response = voucherController.createVoucher(voucherRequest);

        // Then
        assertFalse(response.isSuccess());
    }
}
