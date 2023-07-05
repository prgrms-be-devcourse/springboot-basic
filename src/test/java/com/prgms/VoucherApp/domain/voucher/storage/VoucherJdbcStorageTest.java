package com.prgms.VoucherApp.domain.voucher.storage;

import com.prgms.VoucherApp.domain.voucher.FixedAmountVoucher;
import com.prgms.VoucherApp.domain.voucher.PercentDiscountVoucher;
import com.prgms.VoucherApp.domain.voucher.Voucher;
import com.prgms.VoucherApp.domain.voucher.VoucherType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Transactional
class VoucherJdbcStorageTest {

    @Autowired
    VoucherStorage voucherStorage;

    @Test
    @DisplayName("고정 비용 할인권을 저장한다.")
    void saveFixedVoucher() {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), BigDecimal.valueOf(2500), VoucherType.FIXED_VOUCHER);

        // when
        voucherStorage.save(voucher);
        List<Voucher> vouchers = voucherStorage.findAll();

        // then
        Assertions.assertThat(vouchers).usingRecursiveFieldByFieldElementComparator().containsExactly(voucher);
    }

    @Test
    @DisplayName("퍼센트 비용 할인권을 저장한다.")
    void savePercentVoucher() {
        // given
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), BigDecimal.valueOf(50), VoucherType.PERCENT_VOUCHER);

        // when
        voucherStorage.save(voucher);
        List<Voucher> vouchers = voucherStorage.findAll();

        // then
        Assertions.assertThat(vouchers).usingRecursiveFieldByFieldElementComparator()
            .containsExactly(voucher);
    }

    @Test
    @DisplayName("전체 할인권을 조회한다.")
    void findVouchers() {
        // given
        Voucher voucherA = new FixedAmountVoucher(UUID.randomUUID(), BigDecimal.valueOf(2500), VoucherType.FIXED_VOUCHER);
        Voucher voucherB = new PercentDiscountVoucher(UUID.randomUUID(), BigDecimal.valueOf(50), VoucherType.PERCENT_VOUCHER);

        // when
        voucherStorage.save(voucherA);
        voucherStorage.save(voucherB);
        List<Voucher> vouchers = voucherStorage.findAll();

        // then
        Assertions.assertThat(vouchers).usingRecursiveFieldByFieldElementComparator()
            .containsExactly(voucherA, voucherB);
    }

    @Test
    @DisplayName("개별 할인권을 조회한다.")
    void findVoucher() {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), BigDecimal.valueOf(2500), VoucherType.FIXED_VOUCHER);

        // when
        voucherStorage.save(voucher);
        Optional<Voucher> findVoucher = voucherStorage.findByVoucherId(voucher.getVoucherId());

        // then
        Assertions.assertThat(findVoucher.get()).usingRecursiveComparison().isEqualTo(voucher);
    }

    @Test
    @DisplayName("할인권 조회 실패")
    void findVoucherFail() {
        // given

        // when
        Optional<Voucher> voucherId = voucherStorage.findByVoucherId(UUID.randomUUID());

        // then
        Assertions.assertThat(voucherId).isEmpty();
    }

    @Test
    @DisplayName("고정 비용으로 할인되는 할인권만 조회한다.")
    void findVoucherFixedType() {
        // given
        Voucher voucherA = new FixedAmountVoucher(UUID.randomUUID(), BigDecimal.valueOf(2500), VoucherType.FIXED_VOUCHER);
        Voucher voucherB = new FixedAmountVoucher(UUID.randomUUID(), BigDecimal.valueOf(6000), VoucherType.FIXED_VOUCHER);
        Voucher voucherC = new PercentDiscountVoucher(UUID.randomUUID(), BigDecimal.valueOf(50), VoucherType.PERCENT_VOUCHER);

        // when
        voucherStorage.save(voucherA);
        voucherStorage.save(voucherB);
        voucherStorage.save(voucherC);

        List<Voucher> findFixedVouchers = voucherStorage.findByVoucherType(VoucherType.FIXED_VOUCHER);

        // then
        Assertions.assertThat(findFixedVouchers).usingRecursiveFieldByFieldElementComparator().containsExactly(voucherA, voucherB);
    }

    @Test
    @DisplayName("퍼센트로 할인되는 할인권만 조회한다.")
    void findVoucherPercentType() {
        // given
        Voucher voucherA = new FixedAmountVoucher(UUID.randomUUID(), BigDecimal.valueOf(2500), VoucherType.FIXED_VOUCHER);
        Voucher voucherB = new FixedAmountVoucher(UUID.randomUUID(), BigDecimal.valueOf(6000), VoucherType.FIXED_VOUCHER);
        Voucher voucherC = new PercentDiscountVoucher(UUID.randomUUID(), BigDecimal.valueOf(50), VoucherType.PERCENT_VOUCHER);

        // when
        voucherStorage.save(voucherA);
        voucherStorage.save(voucherB);
        voucherStorage.save(voucherC);

        List<Voucher> findFixedVouchers = voucherStorage.findByVoucherType(VoucherType.PERCENT_VOUCHER);

        // then
        Assertions.assertThat(findFixedVouchers).usingRecursiveFieldByFieldElementComparator().containsExactly(voucherC);
    }

    @Test
    @DisplayName("타입별로 할인권 조회 시 조회되지 않는 경우")
    void findVoucherTypeFail() {
        // given
        Voucher voucherA = new FixedAmountVoucher(UUID.randomUUID(), BigDecimal.valueOf(2500), VoucherType.FIXED_VOUCHER);
        Voucher voucherB = new FixedAmountVoucher(UUID.randomUUID(), BigDecimal.valueOf(6000), VoucherType.FIXED_VOUCHER);

        // when
        voucherStorage.save(voucherA);
        voucherStorage.save(voucherB);

        List<Voucher> findVouchers = voucherStorage.findByVoucherType(VoucherType.PERCENT_VOUCHER);

        // then
        Assertions.assertThat(findVouchers).hasSize(0);
    }

    @Test
    @DisplayName("할인권의 내용을 업데이트 한다.")
    void updateVoucher() {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), BigDecimal.valueOf(2000), VoucherType.FIXED_VOUCHER);
        voucherStorage.save(voucher);

        // when
        voucherStorage.update(new FixedAmountVoucher(voucher.getVoucherId(), BigDecimal.valueOf(5500), VoucherType.FIXED_VOUCHER));

        Optional<Voucher> updatedVoucher = voucherStorage.findByVoucherId(voucher.getVoucherId());

        // then
        Assertions.assertThat(updatedVoucher.get().getAmount()).isEqualTo(BigDecimal.valueOf(5500));
    }

    @Test
    @DisplayName("할인권을 삭제한다.")
    void deleteVoucher() {
        // given

        Voucher voucherA = new FixedAmountVoucher(UUID.randomUUID(), BigDecimal.valueOf(2500), VoucherType.FIXED_VOUCHER);
        Voucher voucherB = new FixedAmountVoucher(UUID.randomUUID(), BigDecimal.valueOf(6000), VoucherType.FIXED_VOUCHER);
        Voucher voucherC = new PercentDiscountVoucher(UUID.randomUUID(), BigDecimal.valueOf(50), VoucherType.PERCENT_VOUCHER);

        // when
        voucherStorage.save(voucherA);
        voucherStorage.save(voucherB);
        voucherStorage.save(voucherC);

        voucherStorage.deleteById(voucherA.getVoucherId());
        List<Voucher> findVouchers = voucherStorage.findAll();

        // then
        Assertions.assertThat(findVouchers).hasSize(2);
        Assertions.assertThat(findVouchers).usingRecursiveFieldByFieldElementComparator().doesNotContain(voucherA);
        Assertions.assertThat(findVouchers).usingRecursiveFieldByFieldElementComparator().contains(voucherB, voucherC);
    }
}