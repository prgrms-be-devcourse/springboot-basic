package com.prgrms.springbootbasic.domain.voucher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.prgrms.springbootbasic.enums.VoucherType;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FixedVoucherTest {

    //FixedVoucher 클래스의 메소드 테스트
    @Test
    @DisplayName("Fixed Voucher 금액 생성 확인 테스트")
    void validDiscount() {
        //given
        long validDiscount = 10000;

        //when
        FixedVoucher fixedVoucher = new FixedVoucher(validDiscount);

        //then
        assertEquals(validDiscount, fixedVoucher.getDiscount());

    }

    @Test
    @DisplayName("바우처 생성 시 ID값이 null이 아닌지 테스트")
    void getVoucherId() {
        //given
        FixedVoucher fixedVoucher = new FixedVoucher(10000);

        //when
        UUID voucherId = fixedVoucher.getVoucherId();

        //then
        assertNotNull(voucherId);
    }

    @Test
    @DisplayName("0이하의 금액이 입력될 경우 테스트")
    void getDiscount() {
        //given
        long invalidDiscount = 0;

        //when

        //then
        assertThrows(IllegalArgumentException.class, () -> new FixedVoucher(invalidDiscount));
    }

    @Test
    @DisplayName("바우처 생성 시 Type이 정상적으로 FIXED로 분류되는지 테스트")
    void getVoucherType() {
        //given
        FixedVoucher fixedVoucher = new FixedVoucher(10000);

        //when

        //then
        assertEquals(VoucherType.FIXED, fixedVoucher.getVoucherType());
    }

    @Test
    @DisplayName("바우처 생성 시 CreateAt이 정상적으로 생성되는지 테스트")
    void getCreatedAt() {
        //given
        FixedVoucher fixedVoucher = new FixedVoucher(10000);

        //when
        LocalDateTime createdAt = fixedVoucher.getCreatedAt();

        //then
        assertNotNull(createdAt);
    }
}