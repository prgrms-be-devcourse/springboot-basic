package com.prgms.VoucherApp.domain.voucher.storage;

import com.prgms.VoucherApp.domain.voucher.model.FixedAmountVoucher;
import com.prgms.VoucherApp.domain.voucher.model.PercentDiscountVoucher;
import com.prgms.VoucherApp.domain.voucher.model.Voucher;
import com.prgms.VoucherApp.domain.voucher.model.VoucherMemoryDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherMemoryDaoTest {

    private VoucherMemoryDao voucherMemoryDao;

    @BeforeEach
    void setUp() {
        voucherMemoryDao = new VoucherMemoryDao();
    }

    @Test
    @DisplayName("고정 비용 할인권 생성 테스트")
    void saveFixedVoucher() {
        // given
        Voucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), BigDecimal.valueOf(1000));

        // when
        voucherMemoryDao.save(fixedVoucher);

        // then
        assertThat(voucherMemoryDao.findById(fixedVoucher.getVoucherId()).get()).isEqualTo(fixedVoucher);
    }

    @Test
    @DisplayName("퍼센트 비율 할인권 생성 테스트")
    void savePercentVoucher() {
        // given
        Voucher percentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), BigDecimal.valueOf(1000));

        // when
        voucherMemoryDao.save(percentVoucher);

        // then
        assertThat(voucherMemoryDao.findById(percentVoucher.getVoucherId()).get()).isEqualTo(percentVoucher);
    }
}