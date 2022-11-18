package org.prgrms.vouchermanagement.voucher.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanagement.voucher.domain.FixedAmountVoucher;
import org.prgrms.vouchermanagement.voucher.domain.PercentDiscountVoucher;
import org.prgrms.vouchermanagement.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class MemoryVoucherRepositoryTest {

    private final MemoryVoucherRepository memoryVoucherRepository = new MemoryVoucherRepository();

    @BeforeEach
    void init() {
        memoryVoucherRepository.clear();
    }

    @Test
    @DisplayName("바우처 저장 및 id 조회 성공")
    void saveVoucherAndFindById() {

        // given
        Voucher fixedAmountVoucher = createFixedAmountVoucher(10, UUID.randomUUID());
        Voucher percentDiscountVoucher = createPercentDiscountVoucher(10, UUID.randomUUID());

        // when
        memoryVoucherRepository.save(fixedAmountVoucher);
        memoryVoucherRepository.save(percentDiscountVoucher);

        Voucher findFixedAmountVoucher = memoryVoucherRepository.findById(fixedAmountVoucher.getVoucherId()).get();
        Voucher findPercentDiscountVoucher = memoryVoucherRepository.findById(percentDiscountVoucher.getVoucherId()).get();

        // then
        assertThat(fixedAmountVoucher).isEqualTo(findFixedAmountVoucher);
        assertThat(percentDiscountVoucher).isEqualTo(findPercentDiscountVoucher);
    }

    @Test
    @DisplayName("찾는 id가 없는 경우 id로 조회")
    void findByIdNotExist() {

        // given
        Voucher fixedAmountVoucher = createFixedAmountVoucher(10, UUID.randomUUID());
        Voucher percentDiscountVoucher = createPercentDiscountVoucher(10, UUID.randomUUID());

        // when
        memoryVoucherRepository.save(fixedAmountVoucher);

        Optional<Voucher> findPercentDiscountVoucher = memoryVoucherRepository.findById(percentDiscountVoucher.getVoucherId());

        // then
        assertThat(findPercentDiscountVoucher).isEmpty();
    }

    @Test
    @DisplayName("모든 바우처 조회")
    void findAll() {

        // given
        Voucher fixedAmountVoucher = createFixedAmountVoucher(10, UUID.randomUUID());
        Voucher percentDiscountVoucher = createPercentDiscountVoucher(10, UUID.randomUUID());

        // when
        memoryVoucherRepository.save(fixedAmountVoucher);
        memoryVoucherRepository.save(percentDiscountVoucher);

        List<Voucher> allVouchers = memoryVoucherRepository.findAll();

        // then
        assertThat(allVouchers).hasSize(2);
        assertThat(allVouchers)
                .extracting("voucherId", "discountAmount", "voucherType")
                .contains(tuple(fixedAmountVoucher.getVoucherId(), fixedAmountVoucher.getDiscountAmount(), fixedAmountVoucher.getVoucherType()),
                        tuple(percentDiscountVoucher.getVoucherId(), percentDiscountVoucher.getDiscountAmount(), percentDiscountVoucher.getVoucherType()));
    }

    @Test
    @DisplayName("저장소가 비어 있을 때 모든 바우처 조회")
    void findAllWhenRepositoryIsEmpty() {

        // when
        List<Voucher> allVouchers = memoryVoucherRepository.findAll();

        // then
        assertThat(allVouchers).isEmpty();
    }

    @Test
    @DisplayName("저장소 비우기")
    void clear() {

        // given
        Voucher fixedAmountVoucher = createFixedAmountVoucher(10, UUID.randomUUID());
        Voucher percentDiscountVoucher = createPercentDiscountVoucher(10, UUID.randomUUID());

        memoryVoucherRepository.save(fixedAmountVoucher);
        memoryVoucherRepository.save(percentDiscountVoucher);

        // when
        memoryVoucherRepository.clear();

        // then
        List<Voucher> allVouchers = memoryVoucherRepository.findAll();
        assertThat(allVouchers).isEmpty();
    }


    private Voucher createFixedAmountVoucher(int discountAmount, UUID customerId) {
        return new FixedAmountVoucher(UUID.randomUUID(), discountAmount, customerId);
    }

    private Voucher createPercentDiscountVoucher(int discountAmount, UUID customerId) {
        return new PercentDiscountVoucher(UUID.randomUUID(), discountAmount, customerId);
    }
}