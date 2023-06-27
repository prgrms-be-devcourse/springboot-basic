package com.wonu606.vouchermanager.service.factory.creator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.wonu606.vouchermanager.domain.PercentageVoucher;
import com.wonu606.vouchermanager.domain.Voucher;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PercentageVoucherCreatorTest {

    @Test
    @DisplayName("PercentageVoucher를 생성한다.")
    void create() {
        // given
        VoucherCreator voucherCreator = new PercentageVoucherCreator();

        // when
        Voucher voucher = voucherCreator.create(UUID.randomUUID(), 50.0d);

        // then
        assertEquals(PercentageVoucher.class, voucher.getClass());
    }
}