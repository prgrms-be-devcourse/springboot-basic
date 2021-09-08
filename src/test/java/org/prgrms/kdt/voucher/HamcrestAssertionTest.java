package org.prgrms.kdt.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class HamcrestAssertionTest {

    @Test
    @DisplayName("Multiple Hamcrest Matcher Test")
    void hamcrestTest() {
        assertEquals(2, 1+1);
        assertThat(1+1, equalTo(2));
        assertThat(1+1, is(2));
        // 상태에 따라 값이 다를 경우 anyOf를 통해 두가지 선언 가능
        assertThat(1+1, anyOf(is(1), is(2)));

        assertNotEquals(1, 1+1);
        // Matcher를 사용하면 not 뒤에 equalTo가 붙을 수 있음
        assertThat(1 + 1, not(1));
    }

    // 값에대한 매칭을 하는 다양한 matcher들을 hamcrest를 통해서 사용할 수 있
    @Test
    @DisplayName("컬렉션에 대한 Matcher 테스트")
    void hamcrestListMatcherTest(){
        var prices = List.of(1,2,3);
        // List의 길이를 구하고 인덱스 값을 할 필요 없이 제공되는 hasSize 사용
        assertThat(prices, hasSize(3));
        // 모든 값을 순회하면서 판별 가능, 이때 1로 바꾸면 오류 발생
        assertThat(prices, everyItem(greaterThan(0)));
        // 순서를 모르는 상태에서 전체 순회, 이떄 없는 값을 입력하면 prices와 매치되지 않는 값이 나와서 오류 발생, 순서가 중요하면 contains
        assertThat(prices, containsInAnyOrder(3, 2, 1));
        // 존재여부 확인하고 greaterThanOrEqualTo라는 matcher를 사용할 수도 있고 직접 Integer 값을 넣을수도 있
        assertThat(prices, hasItem(greaterThanOrEqualTo(2)));
    }

}
