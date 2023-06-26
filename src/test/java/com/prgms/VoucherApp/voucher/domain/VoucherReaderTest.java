package com.prgms.VoucherApp.voucher.domain;

import com.prgms.VoucherApp.domain.voucher.FixedAmountVoucher;
import com.prgms.VoucherApp.domain.voucher.PercentDiscountVoucher;
import com.prgms.VoucherApp.domain.voucher.Voucher;
import com.prgms.VoucherApp.domain.voucher.model.VoucherReader;
import com.prgms.VoucherApp.domain.voucher.storage.VoucherStorage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
class VoucherReaderTest {

    @Autowired
    VoucherReader voucherReader;
    @Autowired
    VoucherStorage voucherStorage;

    @Test
    @DisplayName("voucher 목록 출력")
    void findVoucherListTest() {
        Voucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), BigDecimal.valueOf(1000));
        Voucher percentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), BigDecimal.valueOf(50));

        voucherStorage.save(fixedVoucher);
        voucherStorage.save(percentVoucher);

        Assertions.assertThat(voucherStorage.findAll()).contains(fixedVoucher, percentVoucher);
    }

    @Test
    @DisplayName("VoucherId를 사용하여 1개만 출력")
    void findByVoucherIdTest() {
        UUID uuid = UUID.randomUUID();
        Voucher fixedVoucher = new FixedAmountVoucher(uuid, BigDecimal.valueOf(1000));

        voucherStorage.save(fixedVoucher);

        Assertions.assertThat(voucherReader.readOne(uuid)).isEqualTo(fixedVoucher);
    }

    @Test
    @DisplayName("VoucherId를 사용하여 검색 시 찾을 수 없는 예외 테스트")
    void findByVoucherIdExceptionTest() {
        UUID uuid = UUID.randomUUID();
        Assertions.assertThatThrownBy(() -> voucherReader.readOne(uuid))
                .hasMessageContaining("Voucher Id + " + uuid + " does not exist");
    }
}