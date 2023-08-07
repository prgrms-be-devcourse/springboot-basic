package com.example.voucher.query;

import static com.example.voucher.query.Order.Sort.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTest {

    @DisplayName("ORDER 절 생성 테스트")
    @Test
    void OrderTest() {
        // given when
        Order order = Order.builder()
            .orderBy("VOUCHER_ID")
            .build();

        // then
        assertEquals("ORDER BY VOUCHER_ID ", order.getQuery());
    }

    @DisplayName("ORDER 절 정렬 조건 추가 생성 테스트")
    @Test
    void OrderWithSortTest() {
        // given when
        Order order = Order.builder()
            .orderBy("VOUCHER_ID")
            .setSort(ASC)
            .build();

        // then
        assertEquals("ORDER BY VOUCHER_ID ASC", order.getQuery());
    }

}