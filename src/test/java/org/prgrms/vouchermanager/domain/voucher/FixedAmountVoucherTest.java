package org.prgrms.vouchermanager.domain.voucher;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
class FixedAmountVoucherTest {

    @Test
    void discount() {
        assertThat(1+1, equalTo(2));
    }

    @Test
    public void wrongAmount(){
        List<Integer> lst = Arrays.asList(1,2,3);
        assertThat(lst, hasSize(3));
        assertThat(lst, hasItem(2));
        assertThat(lst, containsInAnyOrder(3,2,1));
        assertThat(lst, everyItem(greaterThan(0)));
    }
}