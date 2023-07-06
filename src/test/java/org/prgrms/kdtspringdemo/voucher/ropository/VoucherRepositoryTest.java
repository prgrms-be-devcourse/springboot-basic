package org.prgrms.kdtspringdemo.voucher.ropository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.kdtspringdemo.voucher.model.entity.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;

import java.util.List;

public class VoucherRepositoryTest {
    VoucherRepository voucherRepository;

    @BeforeEach
    void beforeEach() {
        voucherRepository = new MemoryVoucherRepository();
    }

    @Test
    void 바우처_저장_테스트() {
        //given
        long discount = 1000;

        //when
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(discount);
        Voucher savedVoucher = voucherRepository.save(fixedAmountVoucher);
        Voucher findFixed = voucherRepository.findById(fixedAmountVoucher.getVoucherId()).get();

        //then
        Assertions.assertThat(savedVoucher.toString()).isEqualTo(findFixed.toString());
    }

    @Test
    void 바우처_저장_실패_테스트() {
        //given
        long amount1 = 1000;
        long amount2 = 2000;
        FixedAmountVoucher fixedAmountVoucher1 = new FixedAmountVoucher(amount1);
        FixedAmountVoucher fixedAmountVoucher2 = new FixedAmountVoucher(amount2);

        //when
        voucherRepository.save(fixedAmountVoucher1);
        voucherRepository.save(fixedAmountVoucher2);
        List<Voucher> voucherList = voucherRepository.findAll();

        //then
        Assertions.assertThat(voucherList).hasSize(1);
    }
}
