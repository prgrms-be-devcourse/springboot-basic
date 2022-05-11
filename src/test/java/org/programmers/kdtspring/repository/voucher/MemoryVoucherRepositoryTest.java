package org.programmers.kdtspring.repository.voucher;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.kdtspring.entity.voucher.FixedAmountVoucher;
import org.programmers.kdtspring.entity.voucher.PercentDiscountVoucher;
import org.programmers.kdtspring.entity.voucher.Voucher;
import org.programmers.kdtspring.entity.voucher.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryVoucherRepositoryTest {

    MemoryVoucherRepository memoryVoucherRepository = new MemoryVoucherRepository();

    @AfterEach
    void clean() {
        memoryVoucherRepository.deleteAll();
    }

    @Test
    @DisplayName("FixedAmountVoucher 저장")
    void insertFixedAmountVoucher() {
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 1000, VoucherType.FixedAmountVoucher.name());
        Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID(), 10000, VoucherType.PercentDiscountVoucher.name());

        memoryVoucherRepository.insert(voucher1);
        memoryVoucherRepository.insert(voucher2);

        assertThat(memoryVoucherRepository.findAll())
                .containsExactlyInAnyOrder(voucher1, voucher2);
    }

    @Test
    @DisplayName("PercentDiscountVoucher 저장")
    void insertPercentDiscountVoucher() {
        Voucher voucher1 = new PercentDiscountVoucher(UUID.randomUUID(), 10, VoucherType.PercentDiscountVoucher.name());
        Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 30, VoucherType.PercentDiscountVoucher.name());

        memoryVoucherRepository.insert(voucher1);
        memoryVoucherRepository.insert(voucher2);

        assertThat(memoryVoucherRepository.findAll())
                .containsExactlyInAnyOrder(voucher1, voucher2);
    }

    @Test
    @DisplayName("전체 조회")
    void testFindAll() {
        Voucher voucher1 = new PercentDiscountVoucher(UUID.randomUUID(), 10, VoucherType.PercentDiscountVoucher.name());
        Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID(), 10000, VoucherType.PercentDiscountVoucher.name());
        memoryVoucherRepository.insert(voucher1);
        memoryVoucherRepository.insert(voucher2);

        List<Voucher> all = memoryVoucherRepository.findAll();

        assertThat(all.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("고객을 ID로 조회")
    void testFindById() {
        UUID voucherId = UUID.randomUUID();
        Voucher voucher1 = new FixedAmountVoucher(voucherId, 1000, VoucherType.FixedAmountVoucher.name());
        memoryVoucherRepository.insert(voucher1);

        Optional<Voucher> findVoucher = memoryVoucherRepository.findById(voucherId);

        assertThat(voucher1.getVoucherId()).isEqualTo(findVoucher.get().getVoucherId());
        assertThat(voucher1.getDiscount()).isEqualTo(findVoucher.get().getDiscount());
        assertThat(voucher1.getCustomerId()).isEqualTo(findVoucher.get().getCustomerId());
        assertThat(voucher1.getVoucherType()).isEqualTo(findVoucher.get().getVoucherType());
    }

    @Test
    @DisplayName("하나 삭제")
    void testDeleteOne() {
        Voucher voucher1 = new PercentDiscountVoucher(UUID.randomUUID(), 10, VoucherType.PercentDiscountVoucher.name());
        Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID(), 10000, VoucherType.PercentDiscountVoucher.name());
        memoryVoucherRepository.insert(voucher1);
        memoryVoucherRepository.insert(voucher2);

        memoryVoucherRepository.deleteOne(voucher1);

        assertThat(memoryVoucherRepository.findAll().size()).isEqualTo(1);
    }
}