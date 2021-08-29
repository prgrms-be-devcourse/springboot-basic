package org.prgrms.kdt.voucher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Created by yhh1056
 * Date: 2021/08/25 Time: 2:38 오후
 */
class VoucherTest {

    @Test
    @DisplayName("FixAmountVoucher toString 테스트")
    void fixAmountVoucherToString() {
        UUID voucherId = UUID.randomUUID();
        FixedAmountVoucher voucher = new FixedAmountVoucher(voucherId, 10L);

        assertEquals("FixedAmountVoucher {voucherId = %s, amount = 10}".formatted(voucherId), voucher.toString());

        // hamcrest
        MatcherAssert.assertThat(voucher.toString(), is("FixedAmountVoucher {voucherId = %s, amount = 10}".formatted(voucherId)));
        MatcherAssert.assertThat(voucher.toString(), startsWith("FixedAmountVoucher"));
        MatcherAssert.assertThat(voucher.toString(), endsWith("}"));
        MatcherAssert.assertThat(voucher.toString(),
                allOf(containsString("FixedAmountVoucher"), containsString("voucherId")));

    }

    @Test
    @DisplayName("PercentDiscountVoucher toString 테스트")
    void PercentDiscountVoucherToString() {
        UUID voucherId = UUID.randomUUID();
        PercentDiscountVoucher voucher = new PercentDiscountVoucher(voucherId, 10L);

        assertEquals("PercentDiscountVoucher {voucherId = " + voucherId + ", percent = 10}", voucher.toString());

        // assertj
        assertThat(voucher.toString()).isEqualTo("PercentDiscountVoucher {voucherId = %s, percent = 10}".formatted(voucherId));

    }

}