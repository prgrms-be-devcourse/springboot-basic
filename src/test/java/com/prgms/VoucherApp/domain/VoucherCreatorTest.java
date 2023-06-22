package com.prgms.VoucherApp.domain;

import com.prgms.VoucherApp.model.VoucherCreator;
import com.prgms.VoucherApp.storage.VoucherStorage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class VoucherCreatorTest {

    @Autowired
    VoucherCreator voucherCreator;
    @Autowired
    VoucherStorage voucherStorage;


    @Test
    @DisplayName("할인권 개별 저장 테스트")
    void saveVoucher() {
        Voucher fixedVoucher = voucherCreator.createVoucher(VoucherPolicy.FIXED_VOUCHER, 1000L);
        Voucher percentVoucher = voucherCreator.createVoucher(VoucherPolicy.PERCENT_VOUCHER, 50L);

        Optional<Voucher> findFixedVoucher = voucherStorage.findByVoucherId(fixedVoucher.getUUID());
        Optional<Voucher> findPercentVoucher = voucherStorage.findByVoucherId(percentVoucher.getUUID());

        Assertions.assertThat(findFixedVoucher.get()).isEqualTo(fixedVoucher);
        Assertions.assertThat(findPercentVoucher.get()).isEqualTo(percentVoucher);
    }

    @Test
    @DisplayName("할인권 목록 저장 테스트")
    void saveVouchers() {
        Voucher fixedVoucher = voucherCreator.createVoucher(VoucherPolicy.FIXED_VOUCHER, 1000L);
        Voucher percentVoucher = voucherCreator.createVoucher(VoucherPolicy.PERCENT_VOUCHER, 50L);

        Assertions.assertThat(voucherStorage.findAll()).contains(fixedVoucher, percentVoucher);
    }
}