package com.wonu606.vouchermanager.service.factory.creator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.wonu606.vouchermanager.domain.FixedAmountVoucher;
import com.wonu606.vouchermanager.domain.Voucher;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FixedAmountVoucherCreatorTest {

    @Test
    @DisplayName("FixedAmountVoucher를 생성한다.")
    void create() {
        // given
        VoucherCreator voucherCreator = new FixedAmountVoucherCreator();

        // when
        Voucher voucher = voucherCreator.create(UUID.randomUUID(), 5000.0d);

        // then
        assertEquals(FixedAmountVoucher.class, voucher.getClass());
    }
}