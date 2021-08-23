package org.prgrms.kdt.domain.voucher;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {

    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucherTest.class);

    @BeforeAll
    static void setup() {
        logger.info("@BeforeAll -> run once");
    }

    @BeforeEach
    void init() {
        logger.info("@BeforeEach -> run before each test method");
    }

    @Test
    @DisplayName("기본적인 assertEqual  테스트")
    public void nameAssertEqual() throws Exception {
        // given

        // when

        // then
        assertEquals(2, 1 + 1);
    }

    @Test
    @DisplayName("주어진 금액만큼 할인을 해야한다")
    public void testDiscount() throws Exception {
        // given
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        // when
        long result = voucher.discount(1000);
        // then
        assertThat(900).isEqualTo(result);
    }

    @Test
    @DisplayName("할인 금액은 음수가 될 수 없다")
    public void wrongDiscountTest() throws Exception {
        // given

        // when
        // then
        assertThrows(IllegalArgumentException.class,
                () -> new FixedAmountVoucher(UUID.randomUUID(), -100));
    }

    @Test
    @DisplayName("할인된 금액은 마이너스가 될 수 없다")
    public void testNegativeDiscountedAmount() throws Exception {
        // given
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);

        // when
        long price = 900;

        // then
        assertThat(0).isEqualTo(voucher.discount(900));
    }

    @Test
    @DisplayName("유효한 할인 긂액으로만 생성할 수 있다")
    public void testVoucherCreation() throws Exception {
        // given

        // when

        // then
        assertAll("FixedAmountVoucher creation",
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -100)),
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 1000000))
                );
    }
}