package com.prgrms.model.dto;

import com.prgrms.model.voucher.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VoucherRequestTest {


    @Test
    @DisplayName("기본생성자 예외 던지는지 테스트")
    void getExceptionDefaultCreator() {
        assertThrows(IllegalArgumentException.class, () -> new VoucherRequest());
    }

    @Test
    void ofVoucherRequest() {
        VoucherPolicy voucherPolicy = VoucherPolicy.FixedAmountVoucher;
        Discount discount = new Discount(20);
        VoucherRequest voucherRequest = VoucherRequest.of(voucherPolicy, discount);

        assertNotNull(voucherRequest);
        assertEquals(voucherPolicy, voucherRequest.getVoucherPolicy());
        assertEquals(discount, voucherRequest.getDiscount());
    }
}