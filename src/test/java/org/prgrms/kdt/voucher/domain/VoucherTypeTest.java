package org.prgrms.kdt.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.exception.InvalidArgumentException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VoucherTypeTest {

    @Test
    @DisplayName("[성공] 바우처 타입 유효성 검사")
    void findByVoucherType_SUCCESS() {
        // given
        String type = "fixed";

        // then
        VoucherType actual = VoucherType.findByVoucherType(type);

        // when
        assertThat(VoucherType.FIXED).isEqualTo(actual);
    }

    @Test
    @DisplayName("[실패] 바우처 타입 유효성 검사")
    void findByVoucherType_FAIL() {
        assertAll("find voucher type",
                () -> assertThrows(InvalidArgumentException.class, () -> VoucherType.findByVoucherType("fixx")),
                () -> assertThrows(InvalidArgumentException.class, () -> VoucherType.findByVoucherType("percentt")),
                () -> assertThrows(InvalidArgumentException.class, () -> VoucherType.findByVoucherType("f"))
        );
    }

}