package com.programmers.commandline.domain.voucher.entity;

import com.programmers.commandline.domain.voucher.entity.impl.FixedAmountVoucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VoucherTypeTest {

    @Test
    @DisplayName("바우처가 입력 값에 대해서 정확하게 생성되는지 검증하라")
    void createVoucher() {
        //given
        String uuid = UUID.randomUUID().toString();
        Long discount = 10L;

        //when
        Voucher voucher = VoucherType.FIXED_AMOUNT.createVoucher(UUID.fromString(uuid), discount);

        //then
        assertThat(voucher, isA(FixedAmountVoucher.class));
        assertThat(voucher.getId(),is(uuid));
    }

    @Test
    @DisplayName("입력값에 의해서 VoucherType이 생성되어야 한다.")
    void ofNumberTest() {
        //given
        String input = "1";

        //when
        VoucherType voucherType = VoucherType.ofNumber(input);

        //then
        assertThat(voucherType, is(VoucherType.FIXED_AMOUNT));
    }

    @Test
    @DisplayName("입력값이 잘못되었다면 에러를 나타내라")
    void ofNumberWithThree() {
        //given
        String input = "3";

        //when

        //then
        assertThrows(RuntimeException.class, () -> VoucherType.ofNumber(input));
    }
}