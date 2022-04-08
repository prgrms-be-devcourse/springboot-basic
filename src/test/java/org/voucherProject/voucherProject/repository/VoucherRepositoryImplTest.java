package org.voucherProject.voucherProject.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.voucherProject.voucherProject.entity.voucher.FixedAmountVoucher;
import org.voucherProject.voucherProject.entity.voucher.PercentDiscountVoucher;
import org.voucherProject.voucherProject.entity.voucher.Voucher;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VoucherRepositoryImplTest {

    @Autowired
    VoucherRepository voucherRepository;

    @Test
    public void saveAndFind() throws Exception {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1);
        Voucher saveVoucher = voucherRepository.save(voucher);

        UUID saveVoucherId = saveVoucher.getVoucherId();
        Voucher findVoucher = voucherRepository.findById(saveVoucherId).get();

        assertThat(findVoucher).isEqualTo(saveVoucher);
    }

    @Test
    public void saveSame() throws Exception {

        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1);
        Voucher saveVoucher = voucherRepository.save(voucher);

        assertThrows(IOException.class, () -> voucherRepository.save(voucher));
    }

    @Test
    public void findAll() throws Exception {

        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 3);
        Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 2);
        Voucher voucher3 = new FixedAmountVoucher(UUID.randomUUID(), 1);
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.save(voucher3);

        List<Voucher> findAllVoucher = voucherRepository.findAll();

        boolean result1 = findAllVoucher.contains(voucher1);
        boolean result2 = findAllVoucher.contains(voucher2);
        boolean result3 = findAllVoucher.contains(voucher3);

        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
        assertThat(result3).isTrue();


    }

}