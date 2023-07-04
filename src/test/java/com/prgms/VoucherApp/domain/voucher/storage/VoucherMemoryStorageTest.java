package com.prgms.VoucherApp.domain.voucher.storage;

import com.prgms.VoucherApp.domain.voucher.FixedVoucherPolicy;
import com.prgms.VoucherApp.domain.voucher.PercentVoucherPolicy;
import com.prgms.VoucherApp.domain.voucher.Voucher;
import com.prgms.VoucherApp.domain.voucher.VoucherType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherMemoryStorageTest {

    VoucherStorage voucherStorage;

    @BeforeEach
    void setUp() {
        voucherStorage = new VoucherMemoryStorage();
    }

    @Test
    @DisplayName("고정 비용 할인권 생성 테스트")
    void saveFixedVoucher() {
        // given
        Voucher fixedVoucher = new Voucher(UUID.randomUUID(), new FixedVoucherPolicy(BigDecimal.valueOf(2500)), VoucherType.FIXED_VOUCHER);

        // when
        voucherStorage.save(fixedVoucher);
        Optional<Voucher> findVoucher = voucherStorage.findByVoucherId(fixedVoucher.getVoucherId());
        // then
        assertThat(findVoucher.get()).isEqualTo(fixedVoucher);
    }

    @Test
    @DisplayName("퍼센트 비율 할인권 생성 테스트")
    void savePercentVoucher() {
        // given
        Voucher percentVoucher = new Voucher(UUID.randomUUID(), new PercentVoucherPolicy(BigDecimal.valueOf(50)), VoucherType.PERCENT_VOUCHER);

        // when
        voucherStorage.save(percentVoucher);
        Optional<Voucher> findVoucher = voucherStorage.findByVoucherId(percentVoucher.getVoucherId());

        // then
        assertThat(findVoucher.get()).isEqualTo(percentVoucher);
    }
}