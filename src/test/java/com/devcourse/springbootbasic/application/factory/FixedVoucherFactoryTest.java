package com.devcourse.springbootbasic.application.factory;

import com.devcourse.springbootbasic.application.domain.voucher.dto.VoucherType;
import com.devcourse.springbootbasic.application.domain.voucher.factory.FixedVoucherFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

class FixedVoucherFactoryTest {

    @Test
    @DisplayName("고정값 할인 바우처 생성")
    void 고정할인바우처생성테스트() {
        var voucher = new FixedVoucherFactory().create(0.1);
        assertThat(voucher.getVoucherType(), is(VoucherType.FIXED_AMOUNT));
    }

}