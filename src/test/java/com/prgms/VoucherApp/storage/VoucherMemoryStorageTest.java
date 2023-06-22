package com.prgms.VoucherApp.storage;

import com.prgms.VoucherApp.domain.FixedAmountVoucher;
import com.prgms.VoucherApp.domain.PercentDiscountVoucher;
import com.prgms.VoucherApp.domain.Voucher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherMemoryStorageTest {

    VoucherStorage storage;

    @BeforeEach
    void setUp() {
        storage = new VoucherMemoryStorage();
    }

    @Test
    @DisplayName("할인권 저장 개별 테스트")
    void saveVoucher() {

        Voucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L);
        Voucher percentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 50L);

        storage.save(fixedVoucher);
        storage.save(percentVoucher);

        assertThat(storage.findByVoucherId(fixedVoucher.getUUID()).get()).isEqualTo(fixedVoucher);
        assertThat(storage.findByVoucherId(percentVoucher.getUUID()).get()).isEqualTo(percentVoucher);
    }
}