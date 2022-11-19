package org.programmers.program.voucher.model;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VoucherTest {
    @Test
    @DisplayName("바우처 아이디 찾기 기능 테스트")
    void getVoucherIdTest(){
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        Voucher v1 = new FixedAmountVoucher(id1, 100L);
        Voucher v2 = new PercentDiscountVoucher(id2, 100L);

        assertThat(v1.getVoucherId()).isEqualTo(id1);
        assertThat(v2.getVoucherId()).isEqualTo(id2);
    }

    @Test
    @DisplayName("바우처 금액 할인 테스트")
    void discountTest(){
        Voucher v1 = new FixedAmountVoucher(UUID.randomUUID(), 10000L);
        Voucher v2 = new PercentDiscountVoucher(UUID.randomUUID(), 50L);

        assertThat(v1.discount(40000L)).isEqualTo(30000L);
        assertThat(v2.discount(20000L)).isEqualTo(10000L);
    }

    @Test
    @DisplayName("바우처 타입 가져오기 테스트")
    void typeTest(){
        assertThat(Optional.of(VoucherType.FIXED)).isEqualTo(VoucherType.getVoucherType("1"));
        assertThat(Optional.of(VoucherType.PERCENT)).isEqualTo(VoucherType.getVoucherType("2"));
    }
}