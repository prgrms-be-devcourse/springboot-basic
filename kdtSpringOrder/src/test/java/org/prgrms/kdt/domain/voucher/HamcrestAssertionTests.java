package org.prgrms.kdt.domain.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class HamcrestAssertionTests {

    @Test
    @DisplayName("여러 hamcrest matcher 테스트")
    void hamcrestTest() {
        //junit.jupiter.api.Assertions
        assertEquals(2, 1+1);
        assertNotEquals(1, 1+1);

        //hamcrest.Matchers
        assertThat(1+1, equalTo(2));
        assertThat(1+1, is(2));
        assertThat(1+1, anyOf(is(2), is(1))); //1 또는 2이다.

        assertThat(1+1, not(1));
        assertThat(1+1, not(equalTo(1)));
    }

    @Test
    @DisplayName("컬렉션에 대한 matcher 테스트")
    void hamcrestListMatcherTest() {
        List<Integer> prices = List.of(1, 2, 3);
        assertThat(prices, hasSize(3)); //리스트 사이즈가 3인지
        assertThat(prices, everyItem(greaterThan(0))); //모든 아이템이 0보다 큰지
        assertThat(prices, containsInAnyOrder(3,2,1)); //순서 일치 x
        assertThat(prices, contains(1,2,3)); //순서 일치 o
        assertThat(prices, hasItem(2)); //2가 있는지
        assertThat(prices, hasItem(greaterThanOrEqualTo(2)));

    }
}
