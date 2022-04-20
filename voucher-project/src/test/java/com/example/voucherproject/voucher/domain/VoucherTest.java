package com.example.voucherproject.voucher.domain;

import com.example.voucherproject.voucher.enums.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.voucherproject.voucher.enums.VoucherType.FIXED;
import static com.example.voucherproject.voucher.enums.VoucherType.PERCENT;
import static org.assertj.core.api.Assertions.assertThat;

class VoucherTest {
    @Test
    @DisplayName("고정금액 할인 테스트")
    void fixedTypeDiscountTest(){
        var voucher1 = makeVoucher(FIXED,1000);
        Long discountedPrice = voucher1.discount(10000L);
        assertThat(discountedPrice).isEqualTo(9000L);
    }
    @Test
    @DisplayName("퍼센티지 할인 테스트")
    void percentTypeDiscountTest(){
        var voucher1 = makeVoucher(PERCENT,25);
        Long discountedPrice = voucher1.discount(10000L);
        assertThat(discountedPrice).isEqualTo(7500L);
    }

    private Voucher makeVoucher(VoucherType type, long amount){
        return new Voucher(UUID.randomUUID(), type, amount, LocalDateTime.now());
    }

}