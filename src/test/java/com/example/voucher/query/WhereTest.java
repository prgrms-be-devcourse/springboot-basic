package com.example.voucher.query;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.example.voucher.query.operator.Eq;
import com.example.voucher.query.operator.Gt;

class WhereTest {

    @DisplayName("WHERE 절 생성 테스트")
    @Test
    void WhereTest() {
        // given when
        Where where = Where.builder()
            .where(new Eq("VOUCHER_ID", 1))
            .build();

        // then
        assertEquals("WHERE VOUCHER_ID = 1", where.getQuery());
    }

    @DisplayName("WHERE 절 논리 조건문 추가 생성 테스트")
    @Test
    void WhereWithLogicalConditionTest() {
        // given when
        Where where = Where.builder()
            .where(new Eq("VOUCHER_TYPE", "WELCOME"))
            .and(new Gt("CUSTOMER_AGE", 20))
            .or(new Eq("CUSTOMER_TYPE", "VIP"))
            .build();

        // then
        assertEquals("WHERE VOUCHER_TYPE = WELCOME AND CUSTOMER_AGE >= 20 OR CUSTOMER_TYPE = VIP", where.getQuery());
    }

}