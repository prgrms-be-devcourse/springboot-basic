package org.programmers.springbootbasic.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.data.VoucherType;
import org.programmers.springbootbasic.domain.DefaultVoucherFactory;
import org.programmers.springbootbasic.domain.FixedAmountVoucher;
import org.programmers.springbootbasic.domain.PercentDiscountVoucher;
import org.programmers.springbootbasic.domain.Voucher;
import org.programmers.springbootbasic.repository.MemoryVoucherRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VoucherServiceTest {


    @Test
    public void 바우처_생성_테스트() throws Exception {
        //given
        VoucherService voucherService = new VoucherService(new MemoryVoucherRepository(), new DefaultVoucherFactory());

        // when
        Voucher fixedVoucher = voucherService.createVoucher(VoucherType.FIXED, 10);
        Voucher percentVoucher = voucherService.createVoucher(VoucherType.PERCENT, 20);

        //then
        Assertions.assertThat(fixedVoucher).isInstanceOf(FixedAmountVoucher.class);
        Assertions.assertThat(percentVoucher).isInstanceOf(PercentDiscountVoucher.class);

    }

    @Test
    public void 바우처_저장_테스트() throws Exception {
        //given
        VoucherService voucherService = new VoucherService(new MemoryVoucherRepository(), new DefaultVoucherFactory());

        // when
        Voucher percentVoucher = voucherService.createVoucher(VoucherType.PERCENT, 20);
        Voucher savedVoucher = voucherService.lookupVoucher().get(0);

        //then
        Assertions.assertThat(percentVoucher).isEqualTo(savedVoucher);
    }
}