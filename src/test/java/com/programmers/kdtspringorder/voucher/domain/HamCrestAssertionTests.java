package com.programmers.kdtspringorder.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

public class HamCrestAssertionTests {
    @Test
    @DisplayName("여러 hamcrest matcher 테스트")
    public void hamcrestTest(){
        assertThat(1 + 1, equalTo(2));
        assertThat(1 + 1, is(2));
        assertThat(1 + 1, anyOf(is(1), is(2)));

        assertNotEquals(1+1, 1);
        assertThat(1 + 1, not(is(1)));
    }

    @Test
    @DisplayName("컬렉션에 대한 matcher 테스트")
    public void hamcrestListMatcherTest() {
        List<Integer> list = List.of(1, 2, 3);
        assertThat(list, hasSize(3));
        assertThat(list, everyItem(greaterThan(0)));
        assertThat(list, everyItem(greaterThanOrEqualTo(1)));
        assertThat(list, containsInAnyOrder(3,1,2));
        assertThat(list, hasItem(2));
    }
}
