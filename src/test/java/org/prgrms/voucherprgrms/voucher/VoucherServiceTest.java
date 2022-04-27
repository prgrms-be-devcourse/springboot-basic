package org.prgrms.voucherprgrms.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.voucherprgrms.voucher.model.VoucherForm;
import org.prgrms.voucherprgrms.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;


@SpringJUnitConfig
class VoucherServiceTest {

    @Configuration
    @ComponentScan("org.prgrms.voucherprgrms")
    static class Config {
    }

    @Autowired
    VoucherService voucherService;

    @Autowired
    VoucherRepository voucherRepository;

    @BeforeEach
    void init() {
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("voucher 생성")
    void createVoucherTest() {

        //given
        VoucherForm voucherForm = new VoucherForm("FixedAmountVoucher", 1000);
        voucherService.createVoucher(voucherForm);

        var allVouchers = voucherService.findAllVoucher();

        assertThat(allVouchers.isEmpty(), is(false));
        assertThat(allVouchers.get(0).toString(), is(equalTo(voucherForm.getVoucherType())));
        assertThat(allVouchers.get(0).getValue(), is(equalTo(voucherForm.getValue())));
    }


    @Test
    @DisplayName("voucher list creation")
    void listCreationTest() {
        //given
        voucherService.createVoucher(new VoucherForm("FixedAmountVoucher", 1000));
        voucherService.createVoucher(new VoucherForm("PercentDiscountVoucher", 10));
        voucherService.createVoucher(new VoucherForm("PercentDiscountVoucher", 20));
        voucherService.createVoucher(new VoucherForm("FixedAmountVoucher", 10));
        voucherService.createVoucher(new VoucherForm("PercentDiscountVoucher", 70));

        //when
        var allVouchers = voucherService.findAllVoucher();

        //then
        assertThat(allVouchers.isEmpty(), is(false));
        assertThat(allVouchers, hasSize(5));
    }

}