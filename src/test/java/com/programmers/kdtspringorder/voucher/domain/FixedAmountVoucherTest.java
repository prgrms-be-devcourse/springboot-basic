package com.programmers.kdtspringorder.voucher.domain;

import com.programmers.kdtspringorder.voucher.domain.FixedAmountVoucher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {

    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucherTest.class);

    @BeforeAll
    static void setup() {
        logger.info("@BeforeAll - 시작전 한 번만 실행");
    }

    @BeforeEach
    void init() {
        logger.info("@BeforeEach - 매 테스트 마다 실행");
    }

    @Test
    @DisplayName("주어진 금액만큼 할인")
    public void discount() throws Exception {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 30L);

        //when
        long amount = fixedAmountVoucher.discount(100L);

        //then
        Assertions.assertThat(amount).isEqualTo(70L);
    }

    @Test
    @DisplayName("할인 금액은 마이너스가 될 수 없다")
    public void discountWithMinus() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -1));
    }

    @Test
    @DisplayName("디스카운트된 금액은 마이너스가 될 수 없다.")
    public void testMinusDiscountedAmount() throws Exception {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        assertEquals(0, fixedAmountVoucher.discount(900));
    }

    @Test
    @DisplayName("유효한 금액의 바우처만 생성할 수 있다")
    public void testVoucherCreation() throws Exception {
        assertAll("FixedAmountVoucher creation",
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -1)),
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 10001))
        );
    }
}