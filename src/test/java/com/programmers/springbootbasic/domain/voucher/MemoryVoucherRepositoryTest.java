package com.programmers.springbootbasic.domain.voucher;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemoryVoucherRepositoryTest {
    private final VoucherRepository voucherRepository = new MemoryVoucherRepository();

    @AfterEach
    private void clear() {
        voucherRepository.deleteAll();
    }

    @Test
    void Voucher생성_save_성공() {
        // given
        UUID voucherId = UUID.randomUUID();
        String name = "회원가입 5000원 할인 쿠폰";
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime expirationDate = LocalDateTime.now().plusMonths(3);
        int amount = 5_000;
        Voucher voucher = new FixedAmountVoucher(voucherId, name, createdDate, expirationDate, amount);

        // when
        Voucher saved = voucherRepository.save(voucher);

        // then
        assertThat(saved).isNotNull();
    }

    @Test
    void Voucher생성및저장_findAll_성공() {
        // given
        UUID voucherId = UUID.randomUUID();
        String name = "회원가입 5000원 할인 쿠폰";
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime expirationDate = LocalDateTime.now().plusMonths(3);
        int amount = 5_000;
        Voucher voucher = new FixedAmountVoucher(voucherId, name, createdDate, expirationDate, amount);
        Voucher saved = voucherRepository.save(voucher);

        // when
        List<Voucher> all = voucherRepository.findAll();

        // then
        assertThat(all.get(0)).isEqualTo(voucher);
    }
}
