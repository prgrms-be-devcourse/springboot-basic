package org.prgrms.voucherprgrms.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.voucherprgrms.voucher.model.VoucherDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
class VoucherCreatorTest {

    private static final Logger logger = LoggerFactory.getLogger(VoucherCreatorTest.class);

    @Configuration
    static class Config {

        @Bean
        public VoucherCreator voucherCreator() {
            return new VoucherCreator();
        }
    }

    @Autowired
    VoucherCreator voucherCreator;


    @Test
    @DisplayName("voucher 생성")
    public void voucherCreationTest() {
        //given-when

        var fixedOne = voucherCreator.create(new VoucherDTO("FixedAmountVoucher", 100L));
        var percentOne = voucherCreator.create(new VoucherDTO("PercentDiscountVoucher", 35L));

        //then
        assertThat(fixedOne.toString(), is(equalTo("FixedAmountVoucher")));
        assertThat(percentOne.toString(), is(equalTo("PercentDiscountVoucher")));

        assertThat(fixedOne.getValue(), is(equalTo(100L)));
        assertThat(percentOne.getValue(), is(equalTo(35L)));
    }


    @Test
    @DisplayName("유효하지 않은 voucher type")
    public void illegalArguExceptionTest() {
        var illegalOne = new VoucherDTO("illegalType", 100);

        var exception = assertThrows(IllegalArgumentException.class, () -> voucherCreator.create(illegalOne));
        logger.info("Exception Message is '{}'", exception.getMessage());

    }

}