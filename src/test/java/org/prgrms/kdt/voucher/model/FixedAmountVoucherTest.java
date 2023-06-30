package org.prgrms.kdt.voucher.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {

    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucherTest.class);

    @BeforeAll
    static void setup() {
        logger.info("@BeforeAll.");
    }

    @BeforeEach
    void init() {
        logger.info("@BeforeEach.");
    }

    @Test
    @DisplayName("기본적인 assertEqual 테스트")
    void nameAssertEqual() {
        assertEquals(2, 1+1);
    }

    @Test
    @DisplayName("디스카운트된 금액은 마이너스가 될 수 없다.")
    void testMinusDiscountedAmount() {
        var sut = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        assertEquals(0, sut.discount(900));
    }

    @Test
    @DisplayName("할인 금액은 마이너스가 될 수 없다.")
    void testWithMinus() {
        assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -100));
    }

    @Test
    @DisplayName("할인 금액은 마이너스가 될 수 없다.")
    void testVoucherCreation() {
        assertAll("FixedAmountVoucher creation",
            () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 0)),
            () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -100)),
            () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 100000))
        );
    }
}