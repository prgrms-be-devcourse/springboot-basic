package com.programmers.springbootbasic.domain.voucher;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryVoucherRepositoryTest {
    private final VoucherRepository voucherRepository = new MemoryVoucherRepository();

    @ParameterizedTest
    @EnumSource(VoucherType.class)
    void Voucher생성_save_성공(VoucherType voucherType) {
        // given
        UUID voucherId = UUID.randomUUID();
        String name = "회원가입 쿠폰";
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime expirationDate = createdDate.plusMonths(3);
        VoucherDate voucherDate = new VoucherDate(createdDate, expirationDate);

        // when
        Voucher voucher = switch (voucherType) {
            case FIX -> {
                int amount = 5_000;
                yield new FixedAmountVoucher(voucherId, name, voucherDate, amount);
            }
            case PERCENT -> {
                int percent = 30;
                yield new PercentDiscountVoucher(voucherId, name, voucherDate, percent);
            }
        };
        Voucher saved = voucherRepository.save(voucher);
        System.out.println(voucherRepository.hashCode());
        // then
        assertThat(saved).isNotNull();
    }

    @Test
    void Voucher생성및저장_findAll_성공() {
        // given
        UUID voucherId = UUID.randomUUID();
        String name = "회원가입 5000원 할인 쿠폰";
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime expirationDate = createdDate.plusMonths(3);
        VoucherDate voucherDate = new VoucherDate(createdDate, expirationDate);
        int amount = 5_000;
        Voucher voucher = new FixedAmountVoucher(voucherId, name, voucherDate, amount);
        Voucher saved = voucherRepository.save(voucher);

        // when
        List<Voucher> all = voucherRepository.findAll();
        System.out.println(voucherRepository.hashCode());
        // then
        assertThat(all).isNotNull();
    }
}
