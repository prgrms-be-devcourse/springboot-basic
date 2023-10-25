package com.programmers.vouchermanagement.voucher.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VoucherTypeTest {

    @Test
    @DisplayName("없는 바우처 종류를 선택할 시 빈 Optional 객체를 반환한다.")
    void testFindVoucherTypeFailed_ReturnEmptyOptional() {
        //given
        final String desiredVoucherType = "NoCount";

        //when
        final Optional<VoucherType> voucherType = VoucherType.findVoucherType(desiredVoucherType);

        //then
        assertThat(voucherType.isEmpty(), is(true));
    }
}
