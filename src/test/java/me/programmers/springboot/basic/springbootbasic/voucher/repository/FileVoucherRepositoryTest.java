package me.programmers.springboot.basic.springbootbasic.voucher.repository;

import me.programmers.springboot.basic.springbootbasic.voucher.model.FixedAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.PercentAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class FileVoucherRepositoryTest {

    @Test
    void findAllTest() {
        FileVoucherRepository fileVoucherRepository = new FileVoucherRepository();

        fileVoucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), 1000));
        fileVoucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), 1000));
        fileVoucherRepository.save(new PercentAmountVoucher(UUID.randomUUID(), 10));

        List<Voucher> fileVoucherList = fileVoucherRepository.findAll();

        assertThat(fileVoucherList.size(), is(3));
    }

    @Test
    void saveTest() {
        FileVoucherRepository fileVoucherRepository = new FileVoucherRepository();

        Voucher fixVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);

        Voucher savedVoucher = fileVoucherRepository.save(fixVoucher);

        assertThat(savedVoucher, is(fixVoucher));
    }

}