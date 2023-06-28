package com.devcourse.springbootbasic.application.domain.voucher.data;

import com.devcourse.springbootbasic.application.domain.voucher.dto.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("비율값 할인 바우처 생성")
    void 비율값할인바우처생성테스트() {
        var voucher = new PercentDiscountVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, 50);
        assertThat(voucher.getVoucherType(), is(VoucherType.PERCENT_DISCOUNT));
    }

}