package com.prgms.VoucherApp.domain.voucher.storage;

import com.prgms.VoucherApp.domain.voucher.model.FixedAmountVoucher;
import com.prgms.VoucherApp.domain.voucher.model.PercentDiscountVoucher;
import com.prgms.VoucherApp.domain.voucher.model.Voucher;
import com.prgms.VoucherApp.domain.voucher.model.VoucherFileDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(VoucherFileDao.class)
@ActiveProfiles(profiles = "dev")
public class VoucherFileDaoTest {

    @Autowired
    private VoucherFileDao voucherFileDao;

    @Test
    @DisplayName("고정 비용 할인권 생성 테스트")
    void saveFixedVoucher() {
        // given
        Voucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), BigDecimal.valueOf(1000.0));

        // when
        voucherFileDao.save(fixedVoucher);
        Voucher findVoucher = voucherFileDao.findById(fixedVoucher.getVoucherId()).get();

        // then
        assertThat(findVoucher).usingRecursiveComparison().isEqualTo(fixedVoucher);
    }

    @Test
    @DisplayName("퍼센트 비율 할인권 생성 테스트")
    void savePercentVoucher() {
        // given
        Voucher percentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), BigDecimal.valueOf(50.0));

        // when
        voucherFileDao.save(percentVoucher);
        Voucher findVoucher = voucherFileDao.findById(percentVoucher.getVoucherId()).get();

        // then
        assertThat(findVoucher).usingRecursiveComparison().isEqualTo(percentVoucher);
    }
}
