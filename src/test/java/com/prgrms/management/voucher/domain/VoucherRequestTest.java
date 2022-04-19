package com.prgrms.management.voucher.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class VoucherRequestTest {
    @Test
    void FIXED_CustomerRequest_객체_생성() {
        //given
        VoucherRequest fixedRequest = new VoucherRequest("fixed", "1000");
        //then
        Assertions.assertThat(fixedRequest.getVoucherType()).isEqualTo(VoucherType.FIXED);
    }

    @Test
    void NORMAL_CustomerRequest_객체_생성() {
        //given
        VoucherRequest percentRequest = new VoucherRequest("percent", "90");
        //then
        Assertions.assertThat(percentRequest.getVoucherType()).isEqualTo(VoucherType.PERCENT);
    }
}