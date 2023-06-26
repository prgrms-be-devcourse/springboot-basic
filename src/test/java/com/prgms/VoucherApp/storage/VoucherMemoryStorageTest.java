package com.prgms.VoucherApp.storage;

import com.prgms.VoucherApp.domain.FixedAmountVoucher;
import com.prgms.VoucherApp.domain.PercentDiscountVoucher;
import com.prgms.VoucherApp.domain.Voucher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherMemoryStorageTest {

    VoucherStorage storage;

    @BeforeEach
    void setUp() {
        storage = new VoucherMemoryStorage();
    }

    @Test
    @DisplayName("고정 비용 할인권 생성 테스트")
    void saveFixedVoucher() {

        Voucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), BigDecimal.valueOf(1000));

        storage.save(fixedVoucher);

        assertThat(storage.findByVoucherId(fixedVoucher.getUUID()).get()).isEqualTo(fixedVoucher);
    }

    @Test
    @DisplayName("퍼센트 비율 할인권 생성 테스트")
    void savePercentVoucher() {

        Voucher percentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), BigDecimal.valueOf(1000));

        storage.save(percentVoucher);

        assertThat(storage.findByVoucherId(percentVoucher.getUUID()).get()).isEqualTo(percentVoucher);
    }
}