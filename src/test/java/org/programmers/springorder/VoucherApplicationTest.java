package org.programmers.springorder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VoucherApplicationTest {

    @Test
    @DisplayName("Voucher Application 구동 테스트")
    void startVoucherApplicationTest() {
        VoucherApplication voucherApplication = new VoucherApplication();
        voucherApplication.run();
    }

}