package com.example.voucher.query;

import static java.util.Map.of;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.example.voucher.constant.VoucherType;
import com.example.voucher.voucher.model.Voucher;

class InsertTest {

    @Test
    @DisplayName("Insert 절 생성 테스트")
    void insertTest() {
        Insert insert = Insert.into(Voucher.class)
            .values(of(
                "VOUCHER_ID", 1L,
                "DISCOUNT_VALUE", 1000,
                "VOUCHER_TYPE", VoucherType.PERCENT_DISCOUNT
            ));

        // then
        assertEquals(
            "INSERT INTO VOUCHER (VOUCHER_ID, DISCOUNT_VALUE, VOUCHER_TYPE) VALUES (1, 1000, PERCENT_DISCOUNT)",
            insert.getQuery());
    }

}