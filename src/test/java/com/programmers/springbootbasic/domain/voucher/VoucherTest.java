package com.programmers.springbootbasic.domain.voucher;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class VoucherTest {
    @Test
    void 정상입력값_바우처생성_성공() {
        //given
        UUID voucherId = UUID.randomUUID();
        String name = "회원가입 10000원 할인 쿠폰";
        LocalDateTime expirationDate = LocalDateTime.now().plusMonths(3);
        int amount = 10_000;

        // when
        Voucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, name, expirationDate, amount);

        // then
        assertThat(fixedAmountVoucher.getName()).isEqualTo(name);
    }

    @Test
    void 정상입력값최소금액포함_바우처생성_성공() {
        //given
        UUID voucherId = UUID.randomUUID();
        String name = "회원가입 10000원 할인 쿠폰";
        LocalDateTime expirationDate = LocalDateTime.now().plusMonths(3);
        Long minimumPrice = 3_000L;
        int amount = 10_000;

        // when
        Voucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, name, minimumPrice, expirationDate, amount);

        // then
        assertThat(fixedAmountVoucher.getName()).isEqualTo(name);
    }

    @Test
    void 잘못된바우처아이디_바우처생성_예외발생() {
        // given
        UUID voucherId = null;
        String name = "회원가입 10000원 할인 쿠폰";
        LocalDateTime expirationDate = LocalDateTime.now().plusMonths(3);
        int amount = 10_000;

        // when && then
        assertThatThrownBy(() -> new FixedAmountVoucher(voucherId, name, expirationDate, amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 아이디 값");
    }

    @Test
    void 잘못된바우처아이디최소금액포함_바우처생성_예외발생() {
        // given
        UUID voucherId = null;
        String name = "회원가입 10000원 할인 쿠폰";
        LocalDateTime expirationDate = LocalDateTime.now().plusMonths(3);
        Long minimumPrice = 3_000L;
        int amount = 10_000;

        // when && then
        assertThatThrownBy(() -> new FixedAmountVoucher(voucherId, name, minimumPrice, expirationDate, amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 아이디 값");
    }

    @Test
    void 잘못된이름_바우처생성_예외발생() {
        // given
        UUID voucherId = UUID.randomUUID();
        String name = "";
        LocalDateTime expirationDate = LocalDateTime.now().plusMonths(3);
        int amount = 10_000;

        // when && then
        assertThatThrownBy(() -> new FixedAmountVoucher(voucherId, name, expirationDate, amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 이름");
    }

    @Test
    void 잘못된이름최소금액포함_바우처생성_예외발생() {
        // given
        UUID voucherId = UUID.randomUUID();
        String name = "";
        LocalDateTime expirationDate = LocalDateTime.now().plusMonths(3);
        Long minimumPrice = 3_000L;
        int amount = 10_000;

        // when && then
        assertThatThrownBy(() -> new FixedAmountVoucher(voucherId, name, minimumPrice, expirationDate, amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 이름");
    }

    @Test
    void 잘못된유효기간_바우처생성_예외발생() {
        // given
        UUID voucherId = UUID.randomUUID();
        String name = "회원가입 10000원 할인 쿠폰";
        LocalDateTime expirationDate = LocalDateTime.now().minusMonths(3);
        int amount = 10_000;

        // when && then
        assertThatThrownBy(() -> new FixedAmountVoucher(voucherId, name, expirationDate, amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 유효기간");
    }

    @Test
    void 잘못된유효기간최소금액포함_바우처생성_예외발생() {
        // given
        UUID voucherId = UUID.randomUUID();
        String name = "회원가입 10000원 할인 쿠폰";
        LocalDateTime expirationDate = LocalDateTime.now().minusMonths(3);
        Long minimumPrice = 3_000L;
        int amount = 10_000;

        // when && then
        assertThatThrownBy(() -> new FixedAmountVoucher(voucherId, name, minimumPrice, expirationDate, amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 유효기간");
    }
}