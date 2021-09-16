package org.prgrms.kdt.voucher;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * class FixedAmountVoucherTest : SUT(System Under Test)
 * void testDiscount() : MUT(Method Under Test)
 * <p>
 * 테스트 코드 작성 꿀팁 : 어떻게하면 망가뜨릴까! 하는 것을 생각해야합니다.
 */
class FixedAmountVoucherTest { //

    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucherTest.class);

    @BeforeAll // 모든 테스트 전 딱 한번
    static void setup() {
        logger.info("@BeforeAll - run once");
    }

    @BeforeEach
        // 각 매번 테스트할때 초기화가 필요하다 할때 쓰는
    void init() {
        logger.info("@BeforeEach - run before each test method");
    }

    @Test
    @DisplayName("기본적인 assertEquals 테스트 🥰")
        // 원하는 글짜 넣을 수 있음 ㅋㅋ
    void testAssertEqual() { // 꼭 void 여야함. (어떠한 값도 return 하면 안됨)
        assertEquals(2, 1 + 1);
    }

    @Test
    @DisplayName("discount 된 값은 음수가 될 수 없다.")
    void testMinusDiscountedAmount() { // 꼭 void 여야함. (어떠한 값도 return 하면 안됨)
        var sut = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        assertEquals(0, sut.discount(900));
    }

    @Test
    @DisplayName("주어진 금액만큼 할인을 해야한다.")
    void testDiscount() {
        var sut = new FixedAmountVoucher(UUID.randomUUID(), 100);
        assertEquals(900, sut.discount(1000));
    }

    @Test
    @DisplayName("할인 금액은 마이너스가 될 수 없다.")
        // @Disabled // 잠깐 테스트 스킵 하는 방법
    void testWithMinus() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new FixedAmountVoucher(UUID.randomUUID(), -100)); // 발생될 예약을 정의해야함.
    }

    @Test
    @DisplayName("유효한 할인 금액으로만 생성할 수 있다.")
        // @Disabled // 잠깐 테스트 스킵 하는 방법
    void testVoucherCreation() {
        assertAll("FixedAmountVoucher creation",
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -100)),
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 10000000))
        );
    }
}