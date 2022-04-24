package com.prgrms.management.voucher.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

class VoucherRequestTest {

    @Test
    void 성공_CustomerRequest_객체_생성() {
        //given
        VoucherRequest percentRequest = new VoucherRequest("percent", "90");
        //then
        Assertions.assertThat(percentRequest.getVoucherType()).isEqualTo(VoucherType.PERCENT);
    }

    @Test
    void 실패_CustomerRequest_객체_생성() {
        //given
        VoucherRequest percentRequest = new VoucherRequest("gold", "90");
        //then
        Assertions.assertThatThrownBy(()-> new VoucherRequest("gold", "90"))
                .isInstanceOf(NoSuchElementException.class);
    }
}