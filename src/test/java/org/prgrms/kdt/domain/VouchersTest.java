package org.prgrms.kdt.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.strategy.FixedAmountVoucher;
import org.prgrms.kdt.strategy.PercentDiscountVoucher;
import org.prgrms.kdt.strategy.Voucher;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VouchersTest {

    final Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10L);
    final Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);

    Vouchers vouchers;

    @BeforeEach
    void setUp() {
        vouchers = new Vouchers();
    }

    @Test
    @DisplayName("저장된 voucher를 voucherId로 찾기")
    void findById() {
        UUID voucherId = percentDiscountVoucher.getVoucherId();

        vouchers.insert(percentDiscountVoucher);

        assertThat(vouchers.findById(percentDiscountVoucher.getVoucherId()).equals(voucherId));
    }

    @Test
    @DisplayName("저장된 전체 voucher들의 목록 개수 확인")
    void findAll() {
        vouchers.insert(percentDiscountVoucher);
        vouchers.insert(fixedAmountVoucher);

        assertThat(vouchers.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("voucher를 저장하고 반환")
    void insertVoucher() {
        assertThat(vouchers.insert(percentDiscountVoucher).equals(percentDiscountVoucher));
    }
}
