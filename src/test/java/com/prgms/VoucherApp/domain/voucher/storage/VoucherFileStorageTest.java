package com.prgms.VoucherApp.domain.voucher.storage;

import com.prgms.VoucherApp.domain.voucher.FixedVoucherPolicy;
import com.prgms.VoucherApp.domain.voucher.PercentVoucherPolicy;
import com.prgms.VoucherApp.domain.voucher.Voucher;
import com.prgms.VoucherApp.domain.voucher.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {VoucherFileStorage.class})
@ActiveProfiles(profiles = "dev")
public class VoucherFileStorageTest {

    @Autowired
    VoucherStorage voucherStorage;

    @Test
    @DisplayName("고정 비용 할인권 생성 테스트")
    void saveFixedVoucher() {
        // given
        Voucher fixedVoucher = new Voucher(UUID.randomUUID(),
            new FixedVoucherPolicy(BigDecimal.valueOf(1000)),
            VoucherType.FIXED_VOUCHER);

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
        Voucher percentVoucher = new Voucher(UUID.randomUUID(),
            new PercentVoucherPolicy(BigDecimal.valueOf(1000)),
            VoucherType.PERCENT_VOUCHER);

        // when
        voucherStorage.save(percentVoucher);
        Optional<Voucher> findVoucher = voucherStorage.findByVoucherId(percentVoucher.getVoucherId());

        // then
        assertThat(findVoucher.get()).isEqualTo(percentVoucher);
    }
}
