package org.programmers.springorder.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springorder.model.voucher.Voucher;
import org.programmers.springorder.model.voucher.VoucherType;
import org.programmers.springorder.repository.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "command.line.runner.enabled=false")
class JdbcVoucherRepositoryTest {

    @Autowired
    VoucherRepository voucherRepository;

    @BeforeEach
    void setUp() {
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("바우처 저장에 성공한다.")
    void save() {
        // given
        UUID fixedVoucherId = UUID.randomUUID();
        UUID percentVoucherId = UUID.randomUUID();

        Voucher fixedVoucher = Voucher.toVoucher(fixedVoucherId, 1000, VoucherType.FIXED);
        Voucher percentVoucher = Voucher.toVoucher(percentVoucherId, 10, VoucherType.PERCENT);

        // when
        voucherRepository.insert(fixedVoucher);
        voucherRepository.insert(percentVoucher);

        Voucher findFixedVoucher = voucherRepository.findById(fixedVoucherId).get();
        Voucher findPercentVoucher = voucherRepository.findById(percentVoucherId).get();

        // then
        assertThat(findFixedVoucher.getVoucherId()).isEqualTo(fixedVoucherId);
        assertThat(findPercentVoucher.getVoucherId()).isEqualTo(percentVoucherId);
    }

    @Test
    @DisplayName("전체 바우처 조회에 성공한다.")
    void findAll() {
        // given
        Voucher voucher1 = Voucher.toVoucher(UUID.randomUUID(), 1000, VoucherType.FIXED);
        Voucher voucher2 = Voucher.toVoucher(UUID.randomUUID(), 10, VoucherType.PERCENT);
        voucherRepository.insert(voucher1);
        voucherRepository.insert(voucher2);

        // when
        List<Voucher> voucherList = voucherRepository.findAll();

        // then
        assertThat(voucherList).hasSize(2);
    }

    @Test
    @DisplayName("바우처 ID로 바우처 조회에 성공한다.")
    void findById() {
        // given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = Voucher.toVoucher(voucherId, 1000, VoucherType.FIXED);
        voucherRepository.insert(voucher);

        // when
        Optional<Voucher> findVoucher = voucherRepository.findById(voucherId);

        // then
        assertThat(findVoucher).isPresent();
        assertThat(findVoucher.get().getVoucherId()).isEqualTo(voucherId);
    }

    @Test
    @DisplayName("바우처 Type으로 조회에 성공한다.")
    void findByType() {
        // given
        Voucher voucher1 = Voucher.toVoucher(UUID.randomUUID(), 1000, VoucherType.FIXED);
        Voucher voucher2 = Voucher.toVoucher(UUID.randomUUID(), 10, VoucherType.PERCENT);
        voucherRepository.insert(voucher1);
        voucherRepository.insert(voucher2);

        // when
        List<Voucher> findVouchers = voucherRepository.findByType(VoucherType.FIXED);
        Voucher voucher = findVouchers.get(0);

        // then
        assertThat(findVouchers).hasSize(1);
        assertThat(voucher.getVoucherType()).isEqualTo(VoucherType.FIXED);
    }

    @Test
    @DisplayName("바우처 수정에 성공한다.")
    void update() {
        // given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = Voucher.toVoucher(voucherId, 1000, VoucherType.FIXED);
        voucherRepository.insert(voucher);

        // when
        voucher.update(VoucherType.PERCENT, 10);
        Voucher updatedVoucher = voucherRepository.update(voucher);

        // then
        assertThat(updatedVoucher.getVoucherId()).isEqualTo(voucherId);
        assertThat(updatedVoucher.getDiscountValue()).isEqualTo(10);
    }

    @Test
    @DisplayName("바우처 ID로 바우처 삭제에 성공한다.")
    void deleteById() {
        // given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = Voucher.toVoucher(voucherId, 1000, VoucherType.FIXED);
        voucherRepository.insert(voucher);

        // when
        voucherRepository.deleteById(voucherId);
        Optional<Voucher> findVoucher = voucherRepository.findById(voucherId);

        // then
        assertThat(findVoucher).isNotPresent();
    }

}