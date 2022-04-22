package org.prgrms.voucherprgrms.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.voucherprgrms.voucher.model.VoucherDTO;
import org.prgrms.voucherprgrms.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;
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
        VoucherDTO voucherDTO = new VoucherDTO("FixedAmountVoucher", 1000);
        voucherService.createVoucher(voucherDTO);

        var allVouchers = voucherService.findAllVoucher();

        assertThat(allVouchers.isEmpty(), is(false));
        assertThat(allVouchers.get(0).toString(), is(equalTo(voucherDTO.getVoucherType())));
        assertThat(allVouchers.get(0).getValue(), is(equalTo(voucherDTO.getValue())));
    }


    @Test
    @DisplayName("voucher list creation")
    public void listCreationTest() {
        //given
        voucherService.createVoucher(new VoucherDTO("FixedAmountVoucher", 1000));
        voucherService.createVoucher(new VoucherDTO("PercentDiscountVoucher", 10));
        voucherService.createVoucher(new VoucherDTO("PercentDiscountVoucher", 20));
        voucherService.createVoucher(new VoucherDTO("FixedAmountVoucher", 10));
        voucherService.createVoucher(new VoucherDTO("PercentDiscountVoucher", 70));

        //when
        var allVouchers = voucherService.findAllVoucher();

        //then
        assertThat(allVouchers.isEmpty(), is(false));
        assertThat(allVouchers, hasSize(5));
    }

}