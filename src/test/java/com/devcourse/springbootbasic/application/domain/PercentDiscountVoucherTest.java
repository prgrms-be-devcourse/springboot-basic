package com.devcourse.springbootbasic.application.domain;

import com.devcourse.springbootbasic.application.dto.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("비율값 할인 바우처 생성")
    void 비율값할인바우처생성테스트() {
        var voucher = new PercentDiscountVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, 50);
        assertThat(voucher.getVoucherType(), is(VoucherType.PERCENT_DISCOUNT));
    }

}