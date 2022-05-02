package org.prgms.kdtspringvoucher.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {

    @ParameterizedTest
    @DisplayName("할인가는 최대 할인가(100,000)를 넘을 수 없다.")
    @ValueSource(
            longs = {100001L,4781378128L,9999999L}
    )
    void testMaxAmountVoucher(long amount){
        assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), amount,VoucherType.FIXED, LocalDateTime.now()));
    }

    @ParameterizedTest
    @DisplayName("할인은 최소 0보다 낮을 수 없다.")
    @ValueSource(
            longs = {-1L,-4781378128L,-9999999L}
    )
    void testMinAmountVoucher(Long amount) {
        assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), amount,VoucherType.FIXED, LocalDateTime.now()));
    }

    @ParameterizedTest
    @DisplayName("할인가를 확인할 수 있다.")
    @ValueSource(
            longs = {0L,1L,28L,99999L,100000L}
    )
    void testFixedAmountVoucher(Long amount){
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), amount, VoucherType.FIXED, LocalDateTime.now());
        assertThat(fixedAmountVoucher.getAmount(), is(amount));
    }
}