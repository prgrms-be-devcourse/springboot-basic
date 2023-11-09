package com.programmers.springbootbasic.domain.voucher.controller;

import com.programmers.springbootbasic.common.response.CommonResult;
import com.programmers.springbootbasic.domain.voucher.dto.VoucherServiceRequestDto;
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

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Voucher Controller Test")
class VoucherControllerTest {

    private static final UUID VOUCHER_ID = UUID.randomUUID();
    private static final String VOUCHER_TYPE_STR = "1";
    private static final String VALUE_STR = "25";
    private static final LocalDate CREATED_DATE = LocalDate.now();
    @InjectMocks
    private VoucherController voucherController;
    @Mock
    private VoucherService voucherService;


    @Test
    void testCreateVoucherSuccess() {
        // Arrange
        Voucher expectedVoucher = VoucherType.of(1, VOUCHER_ID, 25L, CREATED_DATE);
        when(voucherService.createVoucher(any(VoucherServiceRequestDto.class))).thenReturn(expectedVoucher);
        // Act
        CommonResult<String> actualResult = voucherController.createVoucher(VOUCHER_TYPE_STR, VALUE_STR);
        // Assert
        assertThat(actualResult.isSuccess()).isTrue();
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
        assertThat(actualResult1.isSuccess()).isFalse();
        assertThat(actualResult1.getData()).isEqualTo(ErrorMsg.NUMBER_FORMAT_MISMATCH.getMessage());
        assertThat(actualResult2.isSuccess()).isFalse();
        assertThat(actualResult2.getData()).isEqualTo(ErrorMsg.NUMBER_FORMAT_MISMATCH.getMessage());
    }

    @Test
    void testFindVoucherByIdSuccess() {
        // Arrange
        long expectedVoucherValue = 25L;
        Voucher expectedVoucher = VoucherType.of(1, VOUCHER_ID, expectedVoucherValue, CREATED_DATE);
        when(voucherService.findVoucherById(any(VoucherServiceRequestDto.class))).thenReturn(expectedVoucher);
        // Act
        CommonResult<String> actualResult = voucherController.findVoucherById(VOUCHER_ID.toString());
        // Assert
        assertThat(actualResult.isSuccess()).isTrue();
        assertThat(actualResult.getData()).isEqualTo(expectedVoucher.getInformation());
    }

    @DisplayName("Test findVoucher Fail: When give voucherId not as UUID")
    @Test
    void testFindVoucherByIdFailWhenUUIDMismatch() {
        // Arrange
        String expectedVoucherId = "AAAA-AAAA-AAAA-AAAA";
        // Act
        CommonResult<String> actualResult = voucherController.findVoucherById(expectedVoucherId);
        // Assert
        assertThat(actualResult.isSuccess()).isFalse();
        assertThat(actualResult.getData()).isEqualTo(ErrorMsg.UUID_FORMAT_MISMATCH.getMessage());
    }

    @Test
    void testUpdateVoucherSuccess() {
        // Arrange
        doNothing().when(voucherService).updateVoucher(any(VoucherServiceRequestDto.class));
        // Act
        CommonResult<String> actualResult = voucherController.updateVoucher(VOUCHER_ID.toString(), VALUE_STR);
        // Assert
        assertThat(actualResult.isSuccess()).isTrue();
        assertThat(actualResult.getData()).isEqualTo(CommonResult.getSuccessResult().getData());
    }

    @DisplayName("Test updateVoucher Fail: When give voucherId not as UUID")
    @Test
    void testUpdateVoucherFailWhenUUIDMismatch() {
        // Arrange
        String expectedVoucherId = "AAAA-AAAA-AAAA-AAAA";
        // Act
        CommonResult<String> actualResult = voucherController.updateVoucher(expectedVoucherId, VALUE_STR);
        // Assert
        assertThat(actualResult.isSuccess()).isFalse();
        assertThat(actualResult.getData()).isEqualTo(ErrorMsg.UUID_FORMAT_MISMATCH.getMessage());
    }

    @DisplayName("Test updateVoucher Fail: When give value not as Number")
    @Test
    void testUpdateVoucherFailWhenNumberFormatMismatch() {
        // Arrange
        String expectedVoucherValue = "A";
        // Act
        CommonResult<String> actualResult = voucherController.updateVoucher(VOUCHER_ID.toString(), expectedVoucherValue);
        // Assert
        assertThat(actualResult.isSuccess()).isFalse();
        assertThat(actualResult.getData()).isEqualTo(ErrorMsg.NUMBER_FORMAT_MISMATCH.getMessage());
    }

    @Test
    void testDeleteVoucherSuccess() {
        // Arrange
        doNothing().when(voucherService).deleteVoucher(any(VoucherServiceRequestDto.class));
        // Act
        CommonResult<String> actualResult = voucherController.deleteVoucher(VOUCHER_ID.toString());
        // Assert
        assertThat(actualResult.isSuccess()).isTrue();
        assertThat(actualResult.getData()).isEqualTo(CommonResult.getSuccessResult().getData());
    }

    @DisplayName("Test deleteVoucher Fail: When give voucherId not as UUID")
    @Test
    void testDeleteVoucherFailWhenUUIDMismatch() {
        // Arrange
        String expectedVoucherId = "AAAA-AAAA-AAAA-AAAA";
        // Act
        CommonResult<String> actualResult = voucherController.deleteVoucher(expectedVoucherId);
        // Assert
        assertThat(actualResult.isSuccess()).isFalse();
        assertThat(actualResult.getData()).isEqualTo(ErrorMsg.UUID_FORMAT_MISMATCH.getMessage());
    }
}