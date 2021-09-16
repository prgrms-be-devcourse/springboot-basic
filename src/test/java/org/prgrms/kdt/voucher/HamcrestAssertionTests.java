package org.prgrms.kdt.voucher;

// 햄크레스트 테스트 : 실제 값을 매칭하는 메처들에 대한 라이브러리. 다양한 Matcher를 제공. 대체로 많이 헴크레스트 Assertion 를 많이 사용함.

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class HamcrestAssertionTests {

    @Test
    @DisplayName("여러 hemcrest matcher 테스트")
    void hamcrestTest() {
        assertEquals(2, 1 + 1); // junit

        assertThat(1 + 1, equalTo(2)); // hemcrest : actual(실제값)을 먼저 넣고, 얘로 예상돼 라고 할 것.
        assertThat(1 + 1, is(2)); // 위에랑 같은 코드
        assertThat(1 + 1, anyOf(is(1), is(2))); // 아무거나 될 수 있다. (근데 이거 많이 사용하면 안됨)

        assertNotEquals(1, 1 + 1); // junit
        assertThat(1 + 1, not(equalTo(1))); // hemcrest

    }

    // hemcrest는 list, collection 테스트 하게 편리한 기능들을 제공함
    @Test
    @DisplayName("컬렉션에 대한 matcher 테스트")
    void hamcrestListMatcherTest() {
        var prices = List.of(1,2,3);
        assertThat(prices, hasSize(3)); // size is 3?
        assertThat(prices, everyItem(greaterThan(0))); // 순회하면서 다 검사할 수 도 잇음.
        assertThat(prices, containsInAnyOrder(3, 4, 2)); // 안에 어떤 아이템이 있는지 확인해 볼 수 도 있음. (순서를 알지 못한 상태에서 다 포함하는지 확인 가능함)
        assertThat(prices, hasItem(2)); // 2를 포함하는지 확인가능
        assertThat(prices, hasItem(greaterThanOrEqualTo(2))); // 2랑 같거나 큰게 포함하는지 확인 가능
    }
}
