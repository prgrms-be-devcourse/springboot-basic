package com.example.voucher.query;

import static java.util.Map.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.example.voucher.query.operator.Eq;
import com.example.voucher.voucher.model.Voucher;

class UpdateTest {

    @Test
    @DisplayName("UPDATE 절 생성 테스트")
    void UpdateTest() {
        // given when
        Where where = Where.builder()
            .where(new Eq("VOUCHER_ID", ":voucherId"))
            .build();

        Update update = Update.builder()
            .updateInto(Voucher.class)
            .set(
                of(
                    "DISCOUNT_VALUE", ":discountValue",
                    "VOUCHER_TYPE", ":voucherType"
                )
            )
            .where(where)
            .build();

        // then
        assertEquals("UPDATE VOUCHER SET VOUCHER_TYPE=:voucherType, DISCOUNT_VALUE=:discountValue WHERE VOUCHER_ID = :voucherId", update.getQuery());
    }

}