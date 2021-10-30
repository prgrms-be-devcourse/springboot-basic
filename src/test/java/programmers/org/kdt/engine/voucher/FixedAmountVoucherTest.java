package programmers.org.kdt.engine.voucher;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import programmers.org.kdt.engine.voucher.type.FixedAmountVoucher;

class FixedAmountVoucherTest {

    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucherTest.class);
    @BeforeAll
    static void setup() {
        logger.info("@BeforeAll - run only once");
    }

    @BeforeEach
    void init() {
        logger.info("@BeforeEach - run before every testcase");
    }

    @Test
    @DisplayName("기본적인 assertEqual test")
    void testAssertEqual() { //무조건 void !
        assertEquals(2, 1+1);
    }

    @Test
    @DisplayName("Discount check")
    void testDiscount() {
        var sut = new FixedAmountVoucher(UUID.randomUUID(), 100);
        assertEquals(900,sut.discount(1000));
    }

    @Test
    @DisplayName("Discounted money can not be Minus")
    void testOverDiscount() {
        var sut = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        assertEquals(0,sut.discount(900));
    }

    @Test
    @DisplayName("Check amount condition")
    void testWithMinusAmount() {
        assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -100));
    }

    @Test
    @DisplayName("Check amount condition")
    void testVoucherCreation() {
        assertAll("FixedAmountVoucher creation",
            () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 0)),
            () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -100)),
            () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 10000000))
        );
    }

}