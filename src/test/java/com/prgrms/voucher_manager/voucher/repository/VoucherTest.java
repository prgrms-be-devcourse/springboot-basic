package com.prgrms.voucher_manager.voucher.repository;

import com.prgrms.voucher_manager.exception.WrongVoucherValueException;
import com.prgrms.voucher_manager.voucher.FixedAmountVoucher;
import com.prgrms.voucher_manager.voucher.PercentDiscountVoucher;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

class VoucherTest {

    private static final Logger logger = LoggerFactory.getLogger(VoucherTest.class);

    @BeforeEach
    void setup(){
    }

    @Test
    @DisplayName("기본적인 voucher 생성 테스트")
    void saveVoucher() {
        FixedAmountVoucher fix = new FixedAmountVoucher(UUID.randomUUID(), 10);
        PercentDiscountVoucher percent = new PercentDiscountVoucher(UUID.randomUUID(), 30);

        assertThat(true, is(fix.getValue() == 10));
        assertThat(true, is(percent.getValue() == 20));
    }


    @Test
    @DisplayName("할인 금액은 마이너스가 될 수 없음.")
    void saveVoucherWithMinus() {
        assertThrows(WrongVoucherValueException.class,()-> new FixedAmountVoucher(UUID.randomUUID(),-3));
        assertThrows(WrongVoucherValueException.class,()-> new PercentDiscountVoucher(UUID.randomUUID(),-3));
        logger.info("할인 금액으로 마이너스가 들어온 경우 안됨");
    }

    @Test
    @DisplayName("할인 금액은 최대값을 넘을 수 없다.")
    void saveVoucherWithMAX(){
        assertThrows(WrongVoucherValueException.class,()-> new FixedAmountVoucher(UUID.randomUUID(),10001));
        assertThrows(WrongVoucherValueException.class,()-> new PercentDiscountVoucher(UUID.randomUUID(),101));
        logger.info("할인 금액으로 최대값을 넘은 수가 들어온 경우 안됨");
    }




}