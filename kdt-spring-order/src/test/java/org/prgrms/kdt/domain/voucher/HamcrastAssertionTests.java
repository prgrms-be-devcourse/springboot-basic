package org.prgrms.kdt.domain.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class HamcrastAssertionTests {
    @Test
    @DisplayName("여러 hanmcrest mathcer 테스트")
    void hamcrestTest(){
        assertEquals(2, 1+1);
        assertNotEquals(1, 1 + 1);

        //hamcrest
        assertThat(1 + 1, equalTo(2));
        assertThat(1+1, is(2));
        assertThat(1 + 1, anyOf(is(1), is(2))); // 1 + 1 can be 1 or 2
        assertThat(1 + 1, not(equalTo(1)));
    }

    @Test
    @DisplayName("컬렉션에 대한 matcher 테스트")
    void hamcrestListMatcherTest(){
        List<Integer> prices = List.of(1, 2, 3);
        assertThat(prices, hasSize(3));
        assertThat(prices, everyItem(greaterThan(0)));
        assertThat(prices, containsInAnyOrder(3, 1, 2));
        assertThat(prices, hasItem(greaterThanOrEqualTo(2)));
    }
}
