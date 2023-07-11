package com.programmers.springbootbasic.domain.voucher;

import com.programmers.springbootbasic.service.VoucherType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VoucherTest {
    @Test
    void 정상입력값_바우처생성_성공() {
        //given
        UUID voucherId = UUID.randomUUID();
        VoucherType voucherType = VoucherType.FIX;
        String name = "회원가입 10000원 할인 쿠폰";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusMonths(3);
        VoucherDateTime voucherDateTime = new VoucherDateTime(createdAt, expiredAt);
        int amount = 10_000;

        // when
        Voucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, voucherType, name, voucherDateTime, amount);

        // then
        assertThat(fixedAmountVoucher).isNotNull();
    }

    @Test
    void 정상입력값최소금액포함_바우처생성_성공() {
        //given
        UUID voucherId = UUID.randomUUID();
        VoucherType voucherType = VoucherType.FIX;
        String name = "회원가입 10000원 할인 쿠폰";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusMonths(3);
        VoucherDateTime voucherDateTime = new VoucherDateTime(createdAt, expiredAt);
        Long minimumPrice = 3_000L;
        int amount = 10_000;

        // when
        Voucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, voucherType, name, voucherDateTime, amount);

        // then
        assertThat(fixedAmountVoucher).isNotNull();
    }

    @Test
    void 잘못된바우처아이디_바우처생성_예외발생() {
        // given
        UUID voucherId = null;
        VoucherType voucherType = VoucherType.FIX;
        String name = "회원가입 10000원 할인 쿠폰";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusMonths(3);
        VoucherDateTime voucherDateTime = new VoucherDateTime(createdAt, expiredAt);
        int amount = 10_000;

        // when && then
        assertThatThrownBy(() -> new FixedAmountVoucher(voucherId, voucherType, name, voucherDateTime, amount))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 잘못된바우처아이디최소금액포함_바우처생성_예외발생() {
        // given
        UUID voucherId = null;
        VoucherType voucherType = VoucherType.FIX;
        String name = "회원가입 10000원 할인 쿠폰";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusMonths(3);
        VoucherDateTime voucherDateTime = new VoucherDateTime(createdAt, expiredAt);
        Long minimumPrice = 3_000L;
        int amount = 10_000;

        // when && then
        assertThatThrownBy(() -> new FixedAmountVoucher(voucherId, voucherType, name, minimumPrice, voucherDateTime, amount))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 잘못된이름_바우처생성_예외발생() {
        // given
        UUID voucherId = UUID.randomUUID();
        VoucherType voucherType = VoucherType.FIX;
        String name = "";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusMonths(3);
        VoucherDateTime voucherDateTime = new VoucherDateTime(createdAt, expiredAt);
        int amount = 10_000;

        // when && then
        assertThatThrownBy(() -> new FixedAmountVoucher(voucherId, voucherType, name, voucherDateTime, amount))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 잘못된이름최소금액포함_바우처생성_예외발생() {
        // given
        UUID voucherId = UUID.randomUUID();
        VoucherType voucherType = VoucherType.FIX;
        String name = "";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusMonths(3);
        VoucherDateTime voucherDateTime = new VoucherDateTime(createdAt, expiredAt);
        Long minimumPrice = 3_000L;
        int amount = 10_000;

        // when && then
        assertThatThrownBy(() -> new FixedAmountVoucher(voucherId, voucherType, name, minimumPrice, voucherDateTime, amount))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
