package me.programmers.springboot.basic.springbootbasic.voucher.repository;

import me.programmers.springboot.basic.springbootbasic.voucher.model.FixedAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.PercentAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class MemoryVoucherRepositoryTest {

    @Test
    void findAllTest() {
        MemoryVoucherRepository memoryVoucherRepository = new MemoryVoucherRepository();

        memoryVoucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), 1000));
        memoryVoucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), 1000));
        memoryVoucherRepository.save(new PercentAmountVoucher(UUID.randomUUID(), 10));

        List<Voucher> memoryVoucherList = memoryVoucherRepository.findAll();

        assertThat(memoryVoucherList.size(), is(3));
        assertThat(memoryVoucherList.get(0), is(instanceOf(FixedAmountVoucher.class)));
    }

    @Test
    void saveTest() {
        MemoryVoucherRepository memoryVoucherRepository = new MemoryVoucherRepository();

        Voucher fixVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        Voucher savedVoucher = memoryVoucherRepository.save(fixVoucher);

        assertThat(fixVoucher, is(savedVoucher));
    }

}