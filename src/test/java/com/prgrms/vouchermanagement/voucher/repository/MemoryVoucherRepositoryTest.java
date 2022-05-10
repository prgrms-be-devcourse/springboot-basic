package com.prgrms.vouchermanagement.voucher.repository;

import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryVoucherRepositoryTest {

    MemoryVoucherRepository repository = new MemoryVoucherRepository();

    @Test
    @DisplayName("Voucher를 메모리에 저장한다.")
    void saveTest() {
        // given
        Voucher fixedVoucher = VoucherType.FIXED_DISCOUNT.constructor(5000, LocalDateTime.now());
        Voucher percentVoucher = VoucherType.PERCENT_DISCOUNT.constructor(50, LocalDateTime.now());

        // when
        repository.save(fixedVoucher);
        repository.save(percentVoucher);

        // then
        assertThat(repository.findAll()).contains(fixedVoucher, percentVoucher);
    }

    @Test
    @DisplayName("Voucher를 update한다.")
    void updateTest() {
        // given
        Voucher fixedVoucher = VoucherType.FIXED_DISCOUNT.constructor(5000, LocalDateTime.now());
        Voucher percentVoucher = VoucherType.PERCENT_DISCOUNT.constructor(50, LocalDateTime.now());

        Long fixedVoucherId = repository.save(fixedVoucher);
        Long percentVoucherId = repository.save(percentVoucher);

        // when
        Voucher updateFixedVoucher = VoucherType.FIXED_DISCOUNT.constructor(fixedVoucherId, 7000, LocalDateTime.now());
        Voucher updatePercentVoucher = VoucherType.PERCENT_DISCOUNT.constructor(percentVoucherId, 70, LocalDateTime.now());

        repository.update(updateFixedVoucher);
        repository.update(updatePercentVoucher);

        // then
        Voucher findFixedVoucher = repository.findById(fixedVoucherId).get();
        assertThat(findFixedVoucher.getAmount()).isEqualTo(7000);

        Voucher findPercentVoucher = repository.findById(percentVoucherId).get();
        assertThat(findPercentVoucher.getAmount()).isEqualTo(70);
    }

    @Test
    @DisplayName("Vouhcer를 voucherId로 조회한다.")
    void findByIdTest() {
        // given
        Voucher voucher = VoucherType.FIXED_DISCOUNT.constructor(100000, LocalDateTime.now());
        Long voucherId = repository.save(voucher);

        // when
        Optional<Voucher> findVoucher = repository.findById(voucherId);

        // then
        assertThat(findVoucher).isNotEmpty();
        assertThat(findVoucher.get()).usingRecursiveComparison().ignoringFields("voucherId").isEqualTo(voucher);
    }

    @Test
    @DisplayName("존재하지 않는 Id로 Voucher를 조회하면 Optional.empty()가 반환된다.")
    void findByNotExistsIdTest() {
        // given
        Voucher voucher1 = VoucherType.FIXED_DISCOUNT.constructor(100000, LocalDateTime.now());
        Voucher voucher2 = VoucherType.PERCENT_DISCOUNT.constructor(10, LocalDateTime.now());
        Voucher voucher3 = VoucherType.FIXED_DISCOUNT.constructor(100000, LocalDateTime.now());

        repository.save(voucher1);
        repository.save(voucher2);
        repository.save(voucher3);

        Long wrongVoucherId = -1L;

        // when
        Optional<Voucher> findVoucher = repository.findById(wrongVoucherId);

        // then
        assertThat(findVoucher).isEmpty();
    }

    @Test
    @DisplayName("Voucher를 삭제한다.")
    void removeTest() {
        // given
        Voucher voucher = VoucherType.FIXED_DISCOUNT.constructor(100000, LocalDateTime.now());
        Long voucherId = repository.save(voucher);

        // when
        repository.remove(voucherId);

        // then
        Optional<Voucher> findVoucher = repository.findById(voucherId);
        assertThat(findVoucher).isEmpty();
    }

    @Test
    @DisplayName("모든 Voucher를 조회한다.")
    void findAllTest() {
        // given
        Voucher voucher1 = VoucherType.FIXED_DISCOUNT.constructor(100000, LocalDateTime.now());
        Voucher voucher2 = VoucherType.PERCENT_DISCOUNT.constructor(10, LocalDateTime.now());
        Voucher voucher3 = VoucherType.FIXED_DISCOUNT.constructor(100000, LocalDateTime.now());

        repository.save(voucher1);
        repository.save(voucher2);
        repository.save(voucher3);

        // when
        List<Voucher> allVouchers = repository.findAll();

        // then
        assertThat(allVouchers.size()).isEqualTo(3);
        assertThat(allVouchers).usingRecursiveFieldByFieldElementComparatorIgnoringFields("voucherId").contains(voucher1, voucher2, voucher3);
    }

    @Test
    @DisplayName("전체 Voucher를 조회하는데 저장된 Voucher가 없는 경우 빈 리스트를 반환한다.")
    void findAllEmptyTest() {
        // given
        // 아무 바우처도 저장하지 않는다.

        // when
        List<Voucher> allVouchers = repository.findAll();

        // then
        assertThat(allVouchers).isNotNull();
        assertThat(allVouchers).isEmpty();
    }
}