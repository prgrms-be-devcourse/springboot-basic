package com.prgrms.model.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherRegistryTest {

    @Test
    @DisplayName("바우처 정책이 등록되어 있지 않다면 true를 반환한다.")
    void isEmpty_True() {
        //given
        List<Voucher> list = new ArrayList<>();
        Vouchers vouchers = new Vouchers(list);

        //when
        boolean result = vouchers.isEmpty(list);

        //then
        assertThat(result).isEqualTo(true);
    }
}
