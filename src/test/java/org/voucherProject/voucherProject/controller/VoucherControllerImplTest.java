package org.voucherProject.voucherProject.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.voucherProject.voucherProject.entity.voucher.Voucher;
import org.voucherProject.voucherProject.entity.voucher.VoucherType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class VoucherControllerImplTest {

    @Autowired
    VoucherController voucherController;

    @Test
    public void createVoucher() throws Exception {
        VoucherType voucherType = VoucherType.FIXED;
        long howMuchDiscount = 10;
        Voucher createVoucher = voucherController.createVoucher(voucherType, howMuchDiscount);

        assertThat(createVoucher.getVoucherType()).isEqualTo(voucherType);
        assertThat(createVoucher.getHowMuch()).isEqualTo(howMuchDiscount);
    }

    @Test
    public void findById() throws Exception {

        VoucherType voucherType = VoucherType.FIXED;
        long howMuchDiscount = 10;
        Voucher createVoucher = voucherController.createVoucher(voucherType, howMuchDiscount);

        Voucher findVoucher = voucherController.findById(createVoucher.getVoucherId());

        assertThat(createVoucher).isEqualTo(findVoucher);
    }

    @Test
    public void findAll() throws Exception {

        VoucherType voucherType = VoucherType.FIXED;
        long howMuchDiscount = 10;
        Voucher createVoucher1 = voucherController.createVoucher(voucherType, howMuchDiscount);
        Voucher createVoucher2 = voucherController.createVoucher(voucherType, howMuchDiscount);
        Voucher createVoucher3 = voucherController.createVoucher(voucherType, howMuchDiscount);

        List<Voucher> vouchers = voucherController.findAll();
        assertThat(vouchers.contains(createVoucher1)).isTrue();
        assertThat(vouchers.contains(createVoucher2)).isTrue();
        assertThat(vouchers.contains(createVoucher3)).isTrue();
    }
}