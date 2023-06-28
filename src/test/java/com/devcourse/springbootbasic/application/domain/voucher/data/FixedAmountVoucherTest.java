package com.devcourse.springbootbasic.application.domain.voucher.data;

import com.devcourse.springbootbasic.application.domain.voucher.dto.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("고정값 할인 바우처 생성")
    void 고정값할인바우처생성테스트() {
        var voucher = new FixedAmountVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 50);
        assertThat(voucher.getVoucherType(), is(VoucherType.FIXED_AMOUNT));
    }

}