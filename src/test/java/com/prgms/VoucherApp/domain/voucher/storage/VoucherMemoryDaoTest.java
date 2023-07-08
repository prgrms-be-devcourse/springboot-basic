package com.prgms.VoucherApp.domain.voucher.storage;

import com.prgms.VoucherApp.domain.voucher.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherMemoryDaoTest {

    VoucherDao storage;

    @BeforeEach
    void setUp() {
        storage = new VoucherMemoryDao();
    }

    @Test
    @DisplayName("고정 비용 할인권 생성 테스트")
    void saveFixedVoucher() {
        // given
        Voucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), BigDecimal.valueOf(1000));

        // when
        storage.save(fixedVoucher);

        // then
        assertThat(storage.findById(fixedVoucher.getVoucherId()).get()).isEqualTo(fixedVoucher);
    }

    @Test
    @DisplayName("퍼센트 비율 할인권 생성 테스트")
    void savePercentVoucher() {
        // given
        Voucher percentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), BigDecimal.valueOf(1000));

        // when
        storage.save(percentVoucher);

        // then
        assertThat(storage.findById(percentVoucher.getVoucherId()).get()).isEqualTo(percentVoucher);
    }
}