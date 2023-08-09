package com.example.voucher.query;

import static com.example.voucher.query.Order.Sort.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.example.voucher.query.operator.Eq;
import com.example.voucher.query.operator.Gt;
import com.example.voucher.voucher.model.FixedAmountVoucher;
import com.example.voucher.voucher.model.Voucher;

class SelectTest {

    @Test
    @DisplayName("SELECT 절 생성 테스트")
    void SelectTest() {
        // given when
        Select select = Select.builder()
            .select("voucher_id", "voucher_type", "discount_value")
            .from(Voucher.class)
            .build();

        // then
        assertEquals("SELECT voucher_id, voucher_type, discount_value FROM VOUCHER", select.getQuery());
    }

    @Test
    @DisplayName("SELECT 절 WHERE 조건 추가 생성 테스트")
    void SelectWithWhereTest() {
        // given when
        Where where = Where.builder()
            .where(new Eq("VOUCHER_ID", 1))
            .build();

        Select select = Select.builder()
            .select("*")
            .from(Voucher.class)
            .where(where)
            .build();

        // then
        assertEquals(
            "SELECT * FROM VOUCHER WHERE VOUCHER_ID = 1",
            select.getQuery());
    }

    @Test
    @DisplayName("SELECT 절 WHERE 조건 추가 ORDER 조건 추가 생성 테스트")
    void SelectWithWhereWithOrderTest() {
        // given when
        Where where = Where.builder()
            .where(new Eq("VOUCHER_TYPE", "WELCOME"))
            .and(new Gt("CUSTOMER_AGE", 20))
            .or(new Eq("CUSTOMER_TYPE", "VIP"))
            .build();

        Order order = Order.builder()
            .orderBy("VOUCHER_ID")
            .setSort(ASC)
            .build();

        Select select = Select.builder()
            .select(FixedAmountVoucher.class)
            .from(Voucher.class)
            .where(where)
            .orderBy(order)
            .build();

        //then
        assertEquals(
            "SELECT voucherId, voucherType, amount FROM VOUCHER WHERE VOUCHER_TYPE = WELCOME AND CUSTOMER_AGE >= 20 OR CUSTOMER_TYPE = VIP ORDER BY VOUCHER_ID ASC",
            select.getQuery());
    }

}
