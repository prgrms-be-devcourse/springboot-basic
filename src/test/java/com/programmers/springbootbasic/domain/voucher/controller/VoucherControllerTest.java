package com.programmers.springbootbasic.domain.voucher.controller;

import com.programmers.springbootbasic.common.response.CommonResult;
import com.programmers.springbootbasic.domain.voucher.dto.VoucherRequestDto;
import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.exception.ErrorMsg;
import com.programmers.springbootbasic.domain.voucher.service.VoucherService;
import com.programmers.springbootbasic.domain.voucher.service.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Voucher Controller Test")
class VoucherControllerTest {

    private static final UUID VOUCHER_ID = UUID.randomUUID();
    private static final String VOUCHER_TYPE_STR = "1";
    private static final String VALUE_STR = "25";
    @InjectMocks
    private VoucherController voucherController;
    @Mock
    private VoucherService voucherService;


    @Test
    void testCreateVoucherSuccess() {
        // Arrange
        Voucher expectedVoucher = VoucherType.of(1, VOUCHER_ID, 25L);
        when(voucherService.createVoucher(any(VoucherRequestDto.class))).thenReturn(expectedVoucher);
        // Act
        CommonResult<String> actualResult = voucherController.createVoucher(VOUCHER_TYPE_STR, VALUE_STR);
        // Assert
        assertTrue(actualResult.isSuccess());
    }

    @DisplayName("Test createVoucher Fail: when Number Format Mismatch")
    @Test
    void testCreateVoucherFail() {
        // Arrange
        String expectedVoucherType = "Test";
        String expectedVoucherValue = "Test";
        // Act
        CommonResult<String> actualResult1 = voucherController.createVoucher(expectedVoucherType, VALUE_STR);
        CommonResult<String> actualResult2 = voucherController.createVoucher(VOUCHER_TYPE_STR, expectedVoucherValue);
        // Assert
        assertFalse(actualResult1.isSuccess());
        assertEquals(ErrorMsg.NUMBER_FORMAT_MISMATCH.getMessage(), actualResult1.getData());
        assertFalse(actualResult2.isSuccess());
        assertEquals(ErrorMsg.NUMBER_FORMAT_MISMATCH.getMessage(), actualResult2.getData());
    }

    @Test
    void testFindVoucherByIdSuccess() {
        // Arrange
        long expectedVoucherValue = 25L;
        Voucher expectedVoucher = VoucherType.of(1, VOUCHER_ID, expectedVoucherValue);
        when(voucherService.findVoucherById(any(VoucherRequestDto.class))).thenReturn(expectedVoucher);
        // Act
        CommonResult<String> actualResult = voucherController.findVoucherById(VOUCHER_ID.toString());
        // Assert
        assertTrue(actualResult.isSuccess());
        assertEquals(expectedVoucher.getInformation(), actualResult.getData());
    }

    @DisplayName("Test findVoucher Fail: When give voucherId not as UUID")
    @Test
    void testFindVoucherByIdFailWhenUUIDMismatch() {
        // Arrange
        String expectedVoucherId = "AAAA-AAAA-AAAA-AAAA";
        // Act
        CommonResult<String> actualResult = voucherController.findVoucherById(expectedVoucherId);
        // Assert
        assertFalse(actualResult.isSuccess());
        assertEquals(ErrorMsg.UUID_FORMAT_MISMATCH.getMessage(), actualResult.getData());
    }

    @Test
    void testUpdateVoucherSuccess() {
        // Arrange
        doNothing().when(voucherService).updateVoucher(any(VoucherRequestDto.class));
        // Act
        CommonResult<String> actualResult = voucherController.updateVoucher(VOUCHER_ID.toString(), VALUE_STR);
        // Assert
        assertTrue(actualResult.isSuccess());
        assertEquals(CommonResult.getSuccessResult().getData(), actualResult.getData());
    }

    @DisplayName("Test updateVoucher Fail: When give voucherId not as UUID")
    @Test
    void testUpdateVoucherFailWhenUUIDMismatch() {
        // Arrange
        String expectedVoucherId = "AAAA-AAAA-AAAA-AAAA";
        // Act
        CommonResult<String> actualResult = voucherController.updateVoucher(expectedVoucherId, VALUE_STR);
        // Assert
        assertFalse(actualResult.isSuccess());
        assertEquals(ErrorMsg.UUID_FORMAT_MISMATCH.getMessage(), actualResult.getData());
    }

    @DisplayName("Test updateVoucher Fail: When give value not as Number")
    @Test
    void testUpdateVoucherFailWhenNumberFormatMismatch() {
        // Arrange
        String expectedVoucherValue = "A";
        // Act
        CommonResult<String> actualResult = voucherController.updateVoucher(VOUCHER_ID.toString(), expectedVoucherValue);
        // Assert
        assertFalse(actualResult.isSuccess());
        assertEquals(ErrorMsg.NUMBER_FORMAT_MISMATCH.getMessage(), actualResult.getData());
    }

    @Test
    void testDeleteVoucherSuccess() {
        // Arrange
        doNothing().when(voucherService).deleteVoucher(any(VoucherRequestDto.class));
        // Act
        CommonResult<String> actualResult = voucherController.deleteVoucher(VOUCHER_ID.toString());
        // Assert
        assertTrue(actualResult.isSuccess());
        assertEquals(CommonResult.getSuccessResult().getData(), actualResult.getData());
    }

    @DisplayName("Test deleteVoucher Fail: When give voucherId not as UUID")
    @Test
    void testDeleteVoucherFailWhenUUIDMismatch() {
        // Arrange
        String expectedVoucherId = "AAAA-AAAA-AAAA-AAAA";
        // Act
        CommonResult<String> actualResult = voucherController.deleteVoucher(expectedVoucherId);
        // Assert
        assertFalse(actualResult.isSuccess());
        assertEquals(ErrorMsg.UUID_FORMAT_MISMATCH.getMessage(), actualResult.getData());
    }
}