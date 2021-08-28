package org.prgrms.orderApp.domain.voucher.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

public class HamcrestAssertionTests {

    @Test
    @DisplayName("ERROR hamcrest matcher TEST")
    void hamcrest(){
        assertEquals(2, 1+1);
        assertThat(1+1, equalTo(2));
        assertThat(1+1, is(2));
        assertThat(1+1, anyOf(is(1), is(2)));


        assertNotEquals(1,1+1);
        assertThat(1+1, not(equalTo(1)));
    }

    @Test
    @DisplayName("matcher TEST for collection")
    void hamcrestListMatcherTest(){
        var prices = List.of(1,2,3);
        assertThat(prices, hasSize(3));
        assertThat(prices, everyItem((greaterThan(0))));
        assertThat(prices, containsInAnyOrder(1,3,2));  //순서를 모른다면
        assertThat(prices, hasItem(2));
        assertThat(prices, hasItem(greaterThanOrEqualTo(2)));
    }
}
