package com.programmers.commandline.domain.voucher.entity.impl;

import com.programmers.commandline.domain.voucher.entity.Voucher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PercentDiscountVoucherTest {
    @DisplayName("PercentDiscount 바우처를 생성할 때 정상적인 discount 를 입력 받았을때 제대로 생성되는지 검증하자")
    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 50, 100})
    void of(long discount) {
        //given
        UUID id = UUID.randomUUID();

        //when
        Voucher voucher = PercentDiscountVoucher.of(id, discount, LocalDateTime.now());

        //then
        assertThat(voucher.getId(), is(id.toString()));
    }


    @Test
    @DisplayName("PercentDiscount 바우처를 생성할 때 잘못된 discount 를 입력 받았을때 에러가 발생하는지 검증하자")
    void of1() {
        //given
        UUID id = UUID.randomUUID();
        long discount = -1L;

        //when
        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> PercentDiscountVoucher.of(id, discount, LocalDateTime.now()));
    }
}