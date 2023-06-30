package com.prgms.VoucherApp.storage;

import com.prgms.VoucherApp.domain.FixedAmountVoucher;
import com.prgms.VoucherApp.domain.PercentDiscountVoucher;
import com.prgms.VoucherApp.domain.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {VoucherFileStorage.class})
@ActiveProfiles(profiles = "dev")
public class VoucherFileStorageTest {

    @Autowired
    VoucherStorage storage;

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

        Voucher percentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), BigDecimal.valueOf(50));

        storage.save(percentVoucher);

        assertThat(storage.findByVoucherId(percentVoucher.getUUID()).get()).isEqualTo(percentVoucher);
    }
}
