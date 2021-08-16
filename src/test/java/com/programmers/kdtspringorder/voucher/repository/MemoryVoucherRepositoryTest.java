package com.programmers.kdtspringorder.voucher.repository;

import com.programmers.kdtspringorder.voucher.domain.FixedAmountVoucher;
import com.programmers.kdtspringorder.voucher.domain.PercentDiscountVoucher;
import com.programmers.kdtspringorder.voucher.domain.Voucher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class MemoryVoucherRepositoryTest {

    private final VoucherRepository voucherRepository = new MemoryVoucherRepository();

    @BeforeEach
    public void beforeEach(){

    }

    @Test
    public void findById() throws Exception{
        //given
        UUID voucherId = UUID.randomUUID();
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, 10L);
        voucherRepository.save(fixedAmountVoucher);

        //when
        Optional<Voucher> optionalVoucher = voucherRepository.findById(voucherId);
        Voucher voucher = optionalVoucher.get();

        //then
        assertThat(voucher).isEqualTo(fixedAmountVoucher);

    }

    @Test
    public void findAll() throws Exception{
        //given
        UUID voucherId1 = UUID.randomUUID();
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(voucherId1, 10L);
        UUID voucherId2 = UUID.randomUUID();
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(voucherId2, 10L);
        UUID voucherId3 = UUID.randomUUID();
        FixedAmountVoucher fixedAmountVoucher1 = new FixedAmountVoucher(voucherId3, 10L);

        voucherRepository.save(fixedAmountVoucher);
        voucherRepository.save(fixedAmountVoucher1);
        voucherRepository.save(percentDiscountVoucher);

        //when
        List<Voucher> all = voucherRepository.findAll();

        //then
        assertThat(all).contains(fixedAmountVoucher, fixedAmountVoucher1,percentDiscountVoucher);
    }

    @Test
    public void save() throws Exception{
        //given
        UUID voucherId = UUID.randomUUID();
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, 10L);

        //when
        Voucher save = voucherRepository.save(fixedAmountVoucher);

        //then
        assertThat(save).isEqualTo(fixedAmountVoucher);
    }
}