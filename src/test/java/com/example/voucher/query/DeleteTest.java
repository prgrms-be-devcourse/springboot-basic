package com.example.voucher.query;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.example.voucher.query.operator.Eq;
import com.example.voucher.voucher.model.Voucher;

class DeleteTest {

    @Test
    @DisplayName("DELETE 절 생성 테스트")
    void DeleteTest() {
        // given when
        Delete delete = Delete.builder()
            .delete(Voucher.class)
            .build();

        // then
        assertEquals("DELETE VOUCHER", delete.getQuery());
    }

    @Test
    @DisplayName("DELETE 절 WHERE 조건 추가 생성 테스트")
    void DeleteWithWhereTest() {
        // given when
        Where where = Where.builder()
            .where(new Eq("VOUCHER_ID", 1))
            .build();

        Delete delete = Delete.builder()
            .delete(Voucher.class)
            .where(where)
            .build();

        // then
        assertEquals("DELETE VOUCHER WHERE VOUCHER_ID = 1", delete.getQuery());
    }


}

