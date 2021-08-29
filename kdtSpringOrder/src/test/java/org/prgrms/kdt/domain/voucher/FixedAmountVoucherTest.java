package org.prgrms.kdt.domain.voucher;

import org.junit.jupiter.api.*;
import org.prgrms.kdt.enums.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {

    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucherTest.class);

    @BeforeAll
    static void setUp() {
        logger.info("BeforAll, 단 한번 실행");
    }

    @BeforeEach
    void init() {
        logger.info("BeforeEach, 테스트 마다 실행");

    }

    @Test
    @DisplayName("기본적인 assertEquals 🛠")
    void testAssertEqual() {
        assertEquals(2, 1+1);
    }

    @Test
    @DisplayName("주어진 금액만큼 할인을 해야한다.")
    void testDiscount() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.fromString("882452fe-3aed-4974-91bf-16074681060b"), UUID.randomUUID(), 100, VoucherType.FIXED);
        assertEquals(900,fixedAmountVoucher.discount(1000));
    }

    @Test
    @DisplayName("디스카운트 된 금액은 마이너스가 될 수 없다.")
    void testMinusDiscountAmount() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.fromString("882452fe-3aed-4974-91bf-16074681060b"), UUID.randomUUID(), 1000, VoucherType.FIXED);
        assertEquals(0, fixedAmountVoucher.discount(900));
    }

    @Test
    @DisplayName("유효한 할인 금액으로만 생성할 수 있다.")
    void testVoucherCreation() {
        assertAll("FixedAmountVoucher creation,",
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.fromString("882452fe-3aed-4974-91bf-16074681060b"), UUID.randomUUID(), 0, VoucherType.FIXED)),
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.fromString("882452fe-3aed-4974-91bf-16074681060b"), UUID.randomUUID(), -100, VoucherType.FIXED )),
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.fromString("882452fe-3aed-4974-91bf-16074681060b"), UUID.randomUUID(), 100000, VoucherType.FIXED )));
    }

    @Test
    @DisplayName("할인 금액은 마이너스가 될 수 없다.")
    //@Disabled 테스트를 건너뛸 수 있는 어노테이션
    void testWithMinus() {
        assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.fromString("882452fe-3aed-4974-91bf-16074681060b"), UUID.randomUUID(), -100, VoucherType.FIXED));
    }
}