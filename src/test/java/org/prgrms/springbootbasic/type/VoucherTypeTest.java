package org.prgrms.springbootbasic.type;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class VoucherTypeTest {

    @Test
    void isValidType() {
        assertThat(VoucherType.isValidType("FIXED"), is(true));
        assertThat(VoucherType.isValidType("PERCENT"), is(true));
        assertThat(VoucherType.isValidType("aaaa"), is(false));
    }
}