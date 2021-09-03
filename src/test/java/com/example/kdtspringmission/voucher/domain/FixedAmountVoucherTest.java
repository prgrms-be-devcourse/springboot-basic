package com.example.kdtspringmission.voucher.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.kdtspringmission.customer.domain.Customer;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FixedAmountVoucherTest {

    @Test
    void testFixedAmountVoucher() {
        //given
        Voucher voucherDiscount100 = new FixedAmountVoucher(UUID.randomUUID(), 100L);
        Voucher voucherDiscount200 = new FixedAmountVoucher(UUID.randomUUID(), 200L);

        //when
        long discount100 = voucherDiscount100.discount(10000L);
        long discount200 = voucherDiscount200.discount(10000L);

        //then
        assertThat(discount100).isEqualTo(9900L);
        assertThat(discount200).isEqualTo(9800L);
    }

}
