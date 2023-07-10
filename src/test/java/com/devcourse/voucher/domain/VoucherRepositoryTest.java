package com.devcourse.voucher.domain;

import com.devcourse.voucher.domain.repository.MemoryVoucherRepository;
import com.devcourse.voucher.domain.repository.VoucherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static com.devcourse.voucher.domain.Voucher.Type.FIXED;
import static com.devcourse.voucher.domain.Voucher.Type.PERCENT;
import static org.assertj.core.api.Assertions.assertThat;

class VoucherRepositoryTest {
    private VoucherRepository voucherRepository;

    private final LocalDateTime expiredAt = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        voucherRepository = new MemoryVoucherRepository();
    }

    @Test
    @DisplayName("저장된 바우처와 요청한 바우처가 동일하고 사용하지 않은 상태여야 한다.")
    void saveTest() {
        // given
        int discountAmount = 50;

        Voucher voucher = Voucher.of(discountAmount, expiredAt, FIXED);

        // when
        Voucher saved = voucherRepository.save(voucher);

        // then
        assertThat(saved.getId()).isEqualTo(voucher.getId());
        assertThat(saved.getExpireAt()).isEqualTo(voucher.getExpireAt());
        assertThat(saved.isUsed()).isFalse();
    }

    @Test
    @DisplayName("저장소에서 가져온 바우처들의 개수가 맞아야 한다.")
    void findAllTest() {
        // given
        int start = 50;
        int end = 100;
        int step = 10;

        IntStream.iterate(start, seed -> seed < end, seed -> seed + step)
                .mapToObj(discount -> Voucher.of(discount, expiredAt, PERCENT))
                .forEach(voucherRepository::save);

        // when
        List<Voucher> vouchers = voucherRepository.findAll();

        // then
        assertThat(vouchers).isNotEmpty().hasSize(5);
    }
}
