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

class PercentDiscountVoucherTest {

    @ParameterizedTest
    @DisplayName("할인율은 0이거나 마이너스가 될 수 없다.")
    @ValueSource(longs = {-1, 0})
    void testMinusPercentDiscountVoucher(Long percent) {
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), percent, VoucherType.PERCENT, LocalDateTime.now()));
    }

    @ParameterizedTest
    @DisplayName("할인율은 100%를 넘을 수 없다.")
    @ValueSource(longs = {101, 200})
    void testOver100PercentDiscountVoucher(Long percent) {
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), percent, VoucherType.PERCENT, LocalDateTime.now()));
    }

    @ParameterizedTest
    @DisplayName("할인율 확인")
    @ValueSource(longs = {100, 1,11,22,99})
    void testPercent(Long percent){
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), percent, VoucherType.PERCENT, LocalDateTime.now());
        assertThat(percentDiscountVoucher.getAmount(), is(percent));
    }
}