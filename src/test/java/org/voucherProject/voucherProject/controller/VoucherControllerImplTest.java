package org.voucherProject.voucherProject.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.voucherProject.voucherProject.controller.voucher.VoucherController;
import org.voucherProject.voucherProject.entity.voucher.Voucher;
import org.voucherProject.voucherProject.entity.voucher.VoucherType;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class VoucherControllerImplTest {

    @Autowired
    VoucherController voucherController;

    @Test
    public void createVoucher() throws Exception {
        Voucher voucher = VoucherType.createVoucher(1, 1);
        Voucher createVoucher = voucherController.createVoucher(voucher);

        assertThat(createVoucher.getVoucherType()).isEqualTo(voucher.getVoucherType());
        assertThat(createVoucher.getHowMuch()).isEqualTo(voucher.getHowMuch());
    }

    @Test
    public void findById() throws Exception {

        Voucher voucher = VoucherType.createVoucher(1, 1);
        Voucher createVoucher = voucherController.createVoucher(voucher);

        Voucher findVoucher = voucherController.findById(createVoucher.getVoucherId());

        assertThat(createVoucher).isEqualTo(findVoucher);
    }

    @Test
    public void findAll() throws Exception {

        Voucher voucher = VoucherType.createVoucher(1, 1);
        Voucher voucher2 = VoucherType.createVoucher(1, 1);
        Voucher voucher3 = VoucherType.createVoucher(1, 1);

        Voucher createVoucher1 = voucherController.createVoucher(voucher);
        Voucher createVoucher2 = voucherController.createVoucher(voucher2);
        Voucher createVoucher3 = voucherController.createVoucher(voucher3);

        List<Voucher> vouchers = voucherController.findAll();
        assertThat(vouchers.contains(createVoucher1)).isTrue();
        assertThat(vouchers.contains(createVoucher2)).isTrue();
        assertThat(vouchers.contains(createVoucher3)).isTrue();
    }
}