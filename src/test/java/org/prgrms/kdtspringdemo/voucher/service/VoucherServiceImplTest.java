package org.prgrms.kdtspringdemo.voucher.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;
import org.prgrms.kdtspringdemo.voucher.ropository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VoucherServiceImplTest {

    @Autowired
    VoucherService voucherService;

    @Autowired
    VoucherRepository voucherRepository;

    @Test
    void 바우처_저장_테스트() {
        //given
        long discount = 1000;

        //when
        Voucher fixedVoucher = voucherService.createVoucher(VoucherType.FIXED, discount);
        Voucher findFixed = voucherRepository.findById(fixedVoucher.getVoucherId()).get();

        //then
        Assertions.assertThat(fixedVoucher.toString()).isEqualTo(findFixed.toString());
    }
}
