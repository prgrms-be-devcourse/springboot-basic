package org.promgrammers.springbootbasic.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.promgrammers.springbootbasic.domain.Voucher;
import org.promgrammers.springbootbasic.domain.VoucherType;
import org.promgrammers.springbootbasic.dto.request.CreateVoucherRequest;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherFactoryTest {

    @Test
    @DisplayName("생성 성공 - Type : FIXED")
    void createFixedAmountVoucherTest() throws Exception {

        //given
        VoucherType fixedAmountVoucher = VoucherType.FIXED;
        long discountAmount = 100L;
        CreateVoucherRequest request = new CreateVoucherRequest(fixedAmountVoucher, discountAmount);

        //when
        Voucher createdVoucher = VoucherFactory.createVoucher(request);

        //then
        assertThat(createdVoucher.getVoucherType()).isEqualTo(fixedAmountVoucher);
        assertThat(createdVoucher.getAmount()).isEqualTo(100L);
    }

    @Test
    @DisplayName("생성 성공 - Type : PERCENT")
    void createPercentAmountVoucherTest() throws Exception {

        //given
        VoucherType percentAmountVoucher = VoucherType.PERCENT;
        long discountAmount = 10L;
        CreateVoucherRequest request = new CreateVoucherRequest(percentAmountVoucher, discountAmount);

        //when
        Voucher createdVoucher = VoucherFactory.createVoucher(request);

        //then
        assertThat(createdVoucher.getVoucherType()).isEqualTo(percentAmountVoucher);
        assertThat(createdVoucher.getAmount()).isEqualTo(10L);
    }
}