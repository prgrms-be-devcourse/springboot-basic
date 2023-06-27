package com.wonu606.vouchermanager.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VoucherVOTest {

    @Test
    @DisplayName("할당형태 그대로 생성되어야 한다.")
    void testCreate() {
        // given
        String type = "fixed";
        double value = 123.123d;

        // when
        VoucherVO voucherVO = new VoucherVO(type, value);

        // then
        assertEquals(type, voucherVO.getType());
        assertEquals(value, voucherVO.getValue());
    }
}