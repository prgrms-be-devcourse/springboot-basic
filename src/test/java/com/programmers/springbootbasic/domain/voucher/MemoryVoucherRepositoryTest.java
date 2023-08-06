package com.programmers.springbootbasic.domain.voucher;

import com.programmers.springbootbasic.domain.voucher.Repository.MemoryVoucherRepository;
import com.programmers.springbootbasic.domain.voucher.Repository.VoucherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryVoucherRepositoryTest {
    private final VoucherRepository voucherRepository = new MemoryVoucherRepository();

    @ParameterizedTest
    @EnumSource(VoucherType.class)
    void 바우처생성_바우처저장_성공(VoucherType voucherType) {
        // given
        UUID voucherId = UUID.randomUUID();
        // when
        Voucher voucher = switch (voucherType) {
            case FIX -> {
                int amount = 5_000;
                yield new FixedAmountVoucher(voucherId, amount);
            }
            case PERCENT -> {
                int percent = 30;
                yield new PercentDiscountVoucher(voucherId, percent);
            }
        };
        UUID id = voucherRepository.save(voucher);
        // then
        assertThat(id).isNotNull();
    }

    @Test
    void 바우처생성및저장_모든바우처가져오기_성공() {
        // given
        UUID voucherId = UUID.randomUUID();
        int amount = 5_000;

        Voucher voucher = new FixedAmountVoucher(voucherId, amount);
        voucherRepository.save(voucher);

        // when
        List<Voucher> all = voucherRepository.findAll();

        // then
        assertThat(all).isNotNull();
    }
}
