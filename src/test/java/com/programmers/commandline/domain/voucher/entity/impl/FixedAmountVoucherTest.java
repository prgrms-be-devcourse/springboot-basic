package com.programmers.commandline.domain.voucher.entity.impl;

import com.programmers.commandline.domain.voucher.entity.Voucher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("PercentDiscount 바우처를 생성할 때 정상적인 discount 를 입력 받았을때 제대로 생성되는지 검증하자")
    void of() {
        //given
        UUID id = UUID.randomUUID();
        long discount = 10L;

        //when
        Voucher voucher = FixedAmountVoucher.of(id, discount, LocalDateTime.now());

        //then
        assertThat(voucher.getId(), is(id.toString()));
    }

    @Test
    @DisplayName("FixedAmount 바우처를 생성할 때 잘못된 discount 를 입력 받았을때 에러를 검증하자")
    void of1() {
        //given
        UUID id = UUID.randomUUID();
        long discount = -1;

        //when
        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> FixedAmountVoucher.of(id, discount, LocalDateTime.now()));
    }


}