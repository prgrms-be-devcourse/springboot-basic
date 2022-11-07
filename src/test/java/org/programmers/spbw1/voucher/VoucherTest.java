package org.programmers.spbw1.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class VoucherTest {
    VoucherRepository voucherRepository = new MemoryVoucherRepository();
    private final static int MAX_VALUE = 100000;

    @Test
    @DisplayName("Fixed Amount Voucher Test")
    public void fixedAmountVoucherTest(){

    }
}
