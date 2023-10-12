package com.programmers.springbootbasic.domain.voucher.controller;

import com.programmers.springbootbasic.common.response.model.CommonResult;
import com.programmers.springbootbasic.common.response.model.ListResult;
import com.programmers.springbootbasic.domain.voucher.entity.FixedAmountVoucher;
import com.programmers.springbootbasic.domain.voucher.entity.PercentDiscountVoucher;
import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.exception.ErrorMsg;
import com.programmers.springbootbasic.domain.voucher.repository.MemoryVoucherRepository;
import com.programmers.springbootbasic.domain.voucher.repository.VoucherRepository;
import com.programmers.springbootbasic.domain.voucher.service.VoucherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Voucher Controller Test")
class VoucherControllerTest {

    private VoucherController voucherController;
    private VoucherRepository voucherRepository;

    @BeforeEach
    void init() {
        voucherRepository = new MemoryVoucherRepository();
        voucherController = new VoucherController(new VoucherService(voucherRepository));
    }

    @Test
    void testCreateVoucherSuccess() {
        // Arrange
        String expectedVoucherType = "1";
        String expectedVoucherValue = "25";
        long expectedDiscountValue = 75L;
        // Act
        CommonResult actualResult = voucherController.createVoucher(expectedVoucherType, expectedVoucherValue);
        // Assert
        Voucher actualVoucher = voucherRepository.findAll().get(0);
        assertTrue(actualResult.isSuccess());
        assertTrue(actualVoucher instanceof FixedAmountVoucher);
        assertEquals(expectedDiscountValue, actualVoucher.discount(100L));
    }

    @DisplayName("Test createVoucher Fail: 잘못된 숫자 형식이 voucherType나 value에 입력되었을 때")
    @Test
    void testCreateVoucherFailWhenNumberFormatExceptionRaised() {
        // Arrange
        String expectedTempValue = "a";
        String expectedFailMessage = "잘못된 숫자 형식입니다.";
        // Act
        CommonResult actualResult = voucherController.createVoucher(expectedTempValue, expectedTempValue);
        // Assert
        assertFalse(actualResult.isSuccess());
        assertEquals(expectedFailMessage, actualResult.getMessage());
    }

    @DisplayName("Test createVoucher Fail: FixedAmountVoucher의 value가 0 미만일 때")
    @Test
    void testCreateVoucherFailWhenCreateFixedAmountVoucherInputValueLessThenZero() {
        // Arrange
        String expectedVoucherType = "1";
        String expectedVoucherValue = "-1";
        // Act
        CommonResult actualResult = voucherController.createVoucher(expectedVoucherType, expectedVoucherValue);
        // Assert
        assertFalse(actualResult.isSuccess());
        assertEquals(ErrorMsg.WrongFixedAmountValueInput.getMessage(), actualResult.getMessage());
    }

    @DisplayName("Test createVoucher Fail: PercentDiscountVoucher value가 0 미만 or 100 초과일 때")
    @Test
    void testCreateVoucherFailWhenCreatePercentDiscountVoucherInputValueLessThenZeroOrMoreThanOneHundred() {
        // Arrange
        String expectedVoucherType = "2";
        String expectedVoucherValue1 = "-1";
        String expectedVoucherValue2 = "101";
        // Act
        CommonResult actualResult1 = voucherController.createVoucher(expectedVoucherType, expectedVoucherValue1);
        CommonResult actualResult2 = voucherController.createVoucher(expectedVoucherType, expectedVoucherValue2);
        // Assert
        assertFalse(actualResult1.isSuccess());
        assertEquals(ErrorMsg.WrongPercentDiscountValueInput.getMessage(), actualResult1.getMessage());
        assertFalse(actualResult2.isSuccess());
        assertEquals(ErrorMsg.WrongPercentDiscountValueInput.getMessage(), actualResult2.getMessage());
    }

    @DisplayName("Test findAllVoucher Success")
    @Test
    void testFindAllVoucherSuccess(){
        // Arrange
        UUID expectedUUID = UUID.randomUUID();
        long expectedValue = 10L;
        Voucher expectedVoucher = new PercentDiscountVoucher(expectedUUID, expectedValue);
        voucherRepository.save(expectedVoucher);
        // Act
        ListResult<String> actualResult = voucherController.findAllVoucher();
        // Assert
        assertNotNull(actualResult);
        assertEquals(expectedVoucher.getInformation(), actualResult.getData().get(0));
    }

}