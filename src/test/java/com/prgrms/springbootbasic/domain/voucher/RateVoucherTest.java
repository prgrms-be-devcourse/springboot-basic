package com.prgrms.springbootbasic.domain.voucher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.prgrms.springbootbasic.enums.VoucherType;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RateVoucherTest {

    //RateVoucher 클래스의 모든 메소드 테스트
    @Test
    @DisplayName("Rate Voucher 퍼센트 생성 확인 테스트")
    void validDiscount() {
        //given
        long validDiscount = 60;

        //when
        RateVoucher rateVoucher = new RateVoucher(validDiscount);

        //then
        assertEquals(validDiscount, rateVoucher.getDiscount());
    }

    @Test
    @DisplayName("바우처 생성 시 ID값이 null이 아닌지 테스트")
    void getVoucherId() {
        //given
        RateVoucher rateVoucher = new RateVoucher(60);

        //when
        UUID voucherId = rateVoucher.getVoucherId();

        //then
        assertNotNull(voucherId);
    }

    @Test
    @DisplayName("0이하의 퍼센트가 입력될 경우 테스트")
    void getDiscount() {
        //given
        long invalidDiscount = 0;

        //when

        //then
        assertThrows(IllegalArgumentException.class, () -> new RateVoucher(invalidDiscount));
    }

    @Test
    @DisplayName("바우처 생성 시 Type이 정상적으로 RATE로 분류되는지 테스트")
    void getVoucherType() {
        //given
        RateVoucher rateVoucher = new RateVoucher(60);

        //when

        //then
        assertEquals(VoucherType.RATE, rateVoucher.getVoucherType());
    }

    @Test
    @DisplayName("바우처 생성 시 CreateAt이 정상적으로 생성되는지 테스트")
    void getCreatedAt() {
        //given
        RateVoucher rateVoucher = new RateVoucher(60);

        //when
        LocalDateTime createdAt = rateVoucher.getCreatedAt();

        //then
        assertNotNull(createdAt);
    }
}