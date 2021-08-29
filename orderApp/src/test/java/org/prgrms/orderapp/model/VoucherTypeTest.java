package org.prgrms.orderapp.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class VoucherTypeTest {
    private final static Logger logger = LoggerFactory.getLogger(VoucherTypeTest.class);

    @Test
    @DisplayName("VoucherType.FIXED valid amount should be greater than 0.")
    void testVoucherTypeFixedAmount() {
        assertThat(VoucherType.FIXED.isValidAmount(100), is(true));
        assertThat(VoucherType.FIXED.isValidAmount(0), is(false));
    }

    @Test
    @DisplayName("VoucherType.PERCENT valid amount should be between 0 and 100.")
    void testVoucherTypePercentAmount() {
        assertThat(VoucherType.PERCENT.isValidAmount(100), is(true));
        assertThat(VoucherType.PERCENT.isValidAmount(0), is(true));
        assertThat(VoucherType.PERCENT.isValidAmount(-1), is(false));
        assertThat(VoucherType.PERCENT.isValidAmount(101), is(false));
    }

    @Test
    @DisplayName("VoucherType.isValid() validates voucher type and amount.")
    void testVoucherTypeIsValid() {
        assertThat(VoucherType.isValid("fixed", "100"), is(true));
        assertThat(VoucherType.isValid("fixed", "0"), is(false));
        assertThat(VoucherType.isValid("fixed", "-1"), is(false));
        assertThat(VoucherType.isValid("fixed", "foo"), is(false));
        assertThat(VoucherType.isValid("percent", "100"), is(true));
        assertThat(VoucherType.isValid("percent", "40"), is(true));
        assertThat(VoucherType.isValid("percent", "0"), is(true));
        assertThat(VoucherType.isValid("percent", "-1"), is(false));
        assertThat(VoucherType.isValid("percent", "101"), is(false));
        assertThat(VoucherType.isValid("percent", "bar"), is(false));
        assertThat(VoucherType.isValid("foobar", "100"), is(false));
    }
}