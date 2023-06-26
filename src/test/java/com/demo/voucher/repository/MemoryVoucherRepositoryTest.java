package com.demo.voucher.repository;

import com.demo.voucher.domain.FixedAmountVoucher;
import com.demo.voucher.domain.PercentDiscountVoucher;
import com.demo.voucher.domain.Voucher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

class MemoryVoucherRepositoryTest {
    private final VoucherRepository repository = new MemoryVoucherRepository();

    @Test
    @DisplayName("새로 생성한 바우처가 Voucher Repository로부터 findById를 통해 잘 탐색 되는지 확인하는 테스트")
    void findById() {
        UUID uuid = UUID.randomUUID();
        FixedAmountVoucher voucher = new FixedAmountVoucher(uuid, 2000);
        repository.insert(voucher);

        Assertions.assertThat(repository.findById(uuid)).isPresent();
    }

    @Test
    @DisplayName("Voucher Repository에서 findAll를 통해 전체 바우처를 모두 잘 가져오는지 확인하는 테스트")
    void findAll() {
        // given
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(uuid1, 1000);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(uuid2, 20);

        repository.insert(fixedAmountVoucher);
        repository.insert(percentDiscountVoucher);

        // when
        Map<UUID, Voucher> vouchers = repository.findAll();

        // then
        Assertions.assertThat(vouchers)
                .hasSize(2)
                .contains(Map.entry(uuid1, fixedAmountVoucher))
                .contains(Map.entry(uuid2, percentDiscountVoucher));
    }

    @Test
    @DisplayName("Voucher Repository에 바우처가 정상적으로 추가되는지 확인하는 테스트")
    void insert() {
        UUID uuid = UUID.randomUUID();
        FixedAmountVoucher voucher = new FixedAmountVoucher(uuid, 2000);

        repository.insert(voucher);

        Assertions.assertThat(repository.findAll())
                .hasSize(1)
                .contains(Map.entry(uuid, voucher));
    }
}