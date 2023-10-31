package com.programmers.vouchermanagement.voucher;

import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.exception.VoucherTypeNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class VoucherTypeTest {

    @Test
    @DisplayName("존재하지 않는 할인 종류는 예외를 발생시킨다.")
    void failGetVoucherTypeByName() {

        // given
        String voucherType = "fixxeedd";

        // when - then
        assertThatThrownBy(() -> VoucherType.getVoucherTypeByName(voucherType))
                .isInstanceOf(VoucherTypeNotFoundException.class);
    }
}
