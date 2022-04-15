package org.voucherProject.voucherProject.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.voucherProject.voucherProject.controller.voucher.VoucherController;
import org.voucherProject.voucherProject.entity.voucher.Voucher;
import org.voucherProject.voucherProject.entity.voucher.VoucherDto;
import org.voucherProject.voucherProject.entity.voucher.VoucherType;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherControllerImplTest {

    @Autowired
    VoucherController voucherController;

    private VoucherDto voucherDto;

    private Voucher createVoucher;

    @BeforeAll
    void beforeAll() {
         voucherDto = VoucherDto.builder()
                .voucherType(VoucherType.FIXED)
                .amount(10)
                .build();

         createVoucher = voucherController.createVoucher(voucherDto);
    }

    @Test
    public void createVoucher() throws Exception {
        assertThat(createVoucher.getVoucherType()).isEqualTo(voucherDto.getVoucherType());
        assertThat(createVoucher.getHowMuch()).isEqualTo(voucherDto.getAmount());
    }

    @Test
    public void findById() throws Exception {
//        VoucherDto result = voucherController.findById(createVoucher.getVoucherId());
//
//        assertThat(voucherDto.getAmount()).isEqualTo(result.getAmount());
//        assertThat(voucherDto.getVoucherType()).isEqualTo(result.getVoucherType());
    }

    @Test
    public void findAll() throws Exception {
        long count = voucherController.findAll().stream().count();
        assertThat(count).isEqualTo(1);
    }
}