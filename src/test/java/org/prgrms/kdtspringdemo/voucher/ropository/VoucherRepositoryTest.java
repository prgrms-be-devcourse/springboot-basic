package org.prgrms.kdtspringdemo.voucher.ropository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.entity.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class VoucherRepositoryTest {
    @Autowired
    VoucherRepository voucherRepository;

    @Test
    void 바우처_저장_테스트() {
        //given
        long discount = 1000;

        //when
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), VoucherType.FIXED, discount);
        Voucher savedVoucher = voucherRepository.save(fixedAmountVoucher);
        Voucher findFixed = voucherRepository.findById(fixedAmountVoucher.getVoucherId()).get();

        //then
        Assertions.assertThat(savedVoucher.toString()).isEqualTo(findFixed.toString());
    }
}
