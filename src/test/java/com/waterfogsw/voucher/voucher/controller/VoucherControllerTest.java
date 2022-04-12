package com.waterfogsw.voucher.voucher.controller;

import com.waterfogsw.voucher.exception.InvalidPercentException;
import com.waterfogsw.voucher.exception.InvalidVoucherTypeException;
import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.domain.VoucherType;
import com.waterfogsw.voucher.voucher.service.VoucherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;

import java.util.UUID;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

class VoucherControllerTest {
    @Mock
    VoucherService mockedVoucherService;
    VoucherController voucherController;

    @BeforeEach
    void setup() {
        this.voucherController = new VoucherController(mockedVoucherService);
    }

    @ParameterizedTest
    @EnumSource(VoucherType.class)
    @DisplayName("바우처 생성 테스트 - 유효한 바우처 타입이면 성공")
    public void voucherCreationValidTypeTest(VoucherType type) throws Exception {
        // given
        Double value = 50d;
        when(mockedVoucherService.createVoucher(type, value))
                .thenReturn(new Voucher(UUID.randomUUID(), type, value));

        // when
        Voucher voucher = voucherController.createVoucher(type, value);

        // then
        assertEquals(voucher.getType(), type);
    }

    @Test
    @DisplayName("바우처 생성 테스트 - 유효하지 않은 바우처 타입(null)이면 실패")
    public void voucherCreationInValidTypeTest() {
        // given
        Double value = 50d;

        // when, then
        assertThrows(InvalidVoucherTypeException.class, () -> voucherController.createVoucher(null, value));
    }

    @ParameterizedTest
    @ValueSource(doubles = {1, 10, 50, 100})
    @DisplayName("Percent 바우처 생성테스트 - Percent 범위 내면 성공")
    public void validPercentVoucherCreation(Double percent) throws Exception {
        // given
        VoucherType type = VoucherType.PERCENT_DISCOUNT;
        when(mockedVoucherService.createVoucher(type, percent))
                .thenReturn(new Voucher(UUID.randomUUID(), type, percent));

        // when, then
        assertDoesNotThrow(() -> voucherController.createVoucher(type, percent));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, 0, 101})
    @DisplayName("Percent 바우처 생성테스트 - Percent 범위 밖이면 실패")
    public void inValidPercentVoucherCreation(Double percent) {
        // given
        VoucherType type = VoucherType.PERCENT_DISCOUNT;
        when(mockedVoucherService.createVoucher(type, percent))
                .thenReturn(new Voucher(UUID.randomUUID(), type, percent));

        // when, then
        assertThrows(InvalidPercentException.class, () -> voucherController.createVoucher(type, percent));
    }

    @ParameterizedTest
    @ValueSource(doubles = {1, 1000, 5000})
    @DisplayName("Percent 바우처 생성테스트 - Fixed 범위 내면 성공")
    public void validFixedVoucherCreation(Double amount) throws Exception {
        // given
        VoucherType type = VoucherType.FIXED_AMOUNT;
        when(mockedVoucherService.createVoucher(type, amount))
                .thenReturn(new Voucher(UUID.randomUUID(), type, amount));

        // when, then
        assertDoesNotThrow(() -> voucherController.createVoucher(type, amount));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, 0})
    @DisplayName("Percent 바우처 생성테스트 - Fixed 범위 밖이면 실패")
    public void inValidFixedVoucherCreation(Double percent) {
        // given
        VoucherType type = VoucherType.PERCENT_DISCOUNT;
        when(mockedVoucherService.createVoucher(type, percent))
                .thenReturn(new Voucher(UUID.randomUUID(), type, percent));

        // when, then
        assertThrows(InvalidPercentException.class, () -> voucherController.createVoucher(type, percent));
    }
}