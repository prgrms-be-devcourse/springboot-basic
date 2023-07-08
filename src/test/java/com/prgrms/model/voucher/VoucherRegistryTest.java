package com.prgrms.model.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

class VoucherRegistryTest {

    @Test
    @DisplayName("정책이 등록되어 있지 않다면 true를 반환한다.")
    void isEmpty_True() {
        //given
        List<Voucher> list = new ArrayList<>();
        VoucherRegistry voucherRegistry = new VoucherRegistry(list);

        //when
        boolean result = voucherRegistry.isEmpty(list);

        //then
        assertThat(result).isEqualTo(true);
    }

}
