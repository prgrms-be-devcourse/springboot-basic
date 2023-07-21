package com.prgrms.vouhcer.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.prgrms.voucher.model.Voucher;
import com.prgrms.voucher.model.Vouchers;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VoucherRegistryTest {

    @Test
    @DisplayName("저장소에 등록된 바우가 하나도 없다면 true를 반환한다.")
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
