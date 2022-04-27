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

    long itemValue;
    FixedAmountVoucher fix;
    PercentDiscountVoucher percent;


    @BeforeEach
    void setup(){
        itemValue = 10000;
        fix = new FixedAmountVoucher(UUID.randomUUID(), 10);
        percent = new PercentDiscountVoucher(UUID.randomUUID(), 30);
    }

    @Test
    @DisplayName("기본적인 voucher 생성 테스트")
    void saveVoucher() {
        assertThat(fix, samePropertyValuesAs(new FixedAmountVoucher(fix.getVoucherId(), 10)));
        assertThat(percent, samePropertyValuesAs(new PercentDiscountVoucher(percent.getVoucherId(), 30)));
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

    @Test
    @DisplayName("할인이 제대로 적용되는지 확인")
    void applyDiscount() {
        long discountByFixedAmount = fix.discount(itemValue);
        long discountByPercentAmount = percent.discount(itemValue);

        logger.info("discount By FixedAmount : {}", discountByFixedAmount);
        logger.info("discount By PercentAmount : {}", discountByPercentAmount);
        assertThat(discountByFixedAmount == 9990, is(true));
        assertThat(discountByPercentAmount == 7000, is(true));

    }
}