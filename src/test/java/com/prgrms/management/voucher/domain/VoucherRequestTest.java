package com.prgrms.management.voucher.domain;

import com.prgrms.management.voucher.dto.VoucherRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

class VoucherRequestTest {

    @Test
    void VoucherRequest_객체_생성_성공() {
        //given
        VoucherRequest percentRequest = new VoucherRequest("percent", "90");
        //then
        Assertions.assertThat(percentRequest.getVoucherType()).isEqualTo(VoucherType.PERCENT);
    }

    @Test
    void VoucherRequest_객체_생성_실패() {
        //then
        Assertions.assertThatThrownBy(() -> new VoucherRequest("gold", "90"))
                .isInstanceOf(NoSuchElementException.class);
    }
}