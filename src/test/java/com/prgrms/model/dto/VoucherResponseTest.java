package com.prgrms.model.dto;

import com.prgrms.model.voucher.Discount;
import com.prgrms.model.voucher.FixedAmountVoucher;
import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VoucherResponseTest {
    @Test
    @DisplayName("기본생성자 예외 던지는지 테스트")
    void getExceptionDefaultCreator() {
        assertThrows(IllegalArgumentException.class, () -> new VoucherResponse());
    }

    @Test
    void ofVoucherRequest() {
        UUID voucherId = UUID.randomUUID();
        Voucher createdVoucher = new FixedAmountVoucher(voucherId, new Discount(20), VoucherType.FIXED_AMOUNT_VOUCHER);

        assertEquals(VoucherResponse.of(createdVoucher),VoucherResponse.of(createdVoucher));
    }
}