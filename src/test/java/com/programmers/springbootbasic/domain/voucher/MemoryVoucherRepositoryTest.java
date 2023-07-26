package com.programmers.springbootbasic.domain.voucher;

import com.programmers.springbootbasic.domain.model.Duration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryVoucherRepositoryTest {
    private final VoucherRepository voucherRepository = new MemoryVoucherRepository();

    @ParameterizedTest
    @EnumSource(VoucherType.class)
    void 바우처생성_바우처저장_성공(VoucherType voucherType) {
        // given
        UUID voucherId = UUID.randomUUID();
        String name = "회원가입 쿠폰";
        Long minimumPriceCondition = 0L;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusMonths(3);
        Duration duration = new Duration(createdAt, expiredAt);
        boolean used = false;
        // when
        Voucher voucher = switch (voucherType) {
            case FIX -> {
                int amount = 5_000;
                yield new FixedAmountVoucher(voucherId, voucherType, name, minimumPriceCondition, duration, amount, used);
            }
            case PERCENT -> {
                int percent = 30;
                yield new PercentDiscountVoucher(voucherId, voucherType, name, minimumPriceCondition, duration, percent, used);
            }
        };
        Optional<Voucher> save = voucherRepository.save(voucher);
        // then
        assertThat(save).isPresent();
    }

    @Test
    void 바우처생성및저장_모든바우처가져오기_성공() {
        // given
        UUID voucherId = UUID.randomUUID();
        VoucherType voucherType = VoucherType.FIX;
        String name = "회원가입 5000원 할인 쿠폰";
        Long minimumPriceCondition = 0L;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusMonths(3);
        Duration duration = new Duration(createdAt, expiredAt);
        int amount = 5_000;
        boolean used = false;
        Voucher voucher = new FixedAmountVoucher(voucherId, voucherType, name, minimumPriceCondition, duration, amount, used);
        Optional<Voucher> saved = voucherRepository.save(voucher);

        // when
        List<Voucher> all = voucherRepository.findAll();

        // then
        assertThat(all).isNotNull(); // TODO: Notnull 말고 NOTEmpty로 체크해볼것
    }
}
