package com.programmers.springweekly.repository.voucher;

import static org.assertj.core.api.Assertions.assertThat;

import com.programmers.springweekly.domain.voucher.FixedAmountVoucher;
import com.programmers.springweekly.domain.voucher.PercentDiscountVoucher;
import com.programmers.springweekly.domain.voucher.Voucher;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemoryVoucherRepositoryTest {

    private VoucherRepository voucherRepository;

    @BeforeEach
    void createVoucherRepository() {
        voucherRepository = new MemoryVoucherRepository();
    }

    @Test
    @DisplayName("메모리 저장소에 고정 할인 바우처를 생성하여 저장할 수 있다.")
    void saveFixedVoucherToMemoryRepository() {
        // given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(voucherId, 1000);

        // when
        voucherRepository.save(voucher);
        Voucher voucherActual = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new NoSuchElementException("찾는 바우처가 없습니다."));

        // then
        assertThat(voucherActual)
                .usingRecursiveComparison().isEqualTo(voucher);
    }

    @Test
    @DisplayName("메모리 저장소에 퍼센트 할인 바우처를 샹송허요 저장할 수 있다.")
    void savePercentVoucherToMemoryRepository() {
        // given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new PercentDiscountVoucher(voucherId, 100);

        // when
        voucherRepository.save(voucher);
        Voucher voucherActual = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new NoSuchElementException("찾는 바우처가 없습니다."));

        // then
        assertThat(voucherActual)
                .usingRecursiveComparison().isEqualTo(voucher);
    }

    @Test
    @DisplayName("메모리 저장소에 여러 바우처를 생성하여 저장했을 때 저장된 모든 바우처를 가져올 수 있다.")
    void getVoucherList() {
        // given
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID(), 500);
        Voucher voucher3 = new PercentDiscountVoucher(UUID.randomUUID(), 50);
        Voucher voucher4 = new PercentDiscountVoucher(UUID.randomUUID(), 30);

        // when
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.save(voucher3);
        voucherRepository.save(voucher4);

        // then
        assertThat(voucherRepository.findAll()).contains(voucher1, voucher2, voucher3, voucher4);
    }

}
