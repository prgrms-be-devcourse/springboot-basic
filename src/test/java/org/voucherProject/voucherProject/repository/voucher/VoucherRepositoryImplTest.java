package org.voucherProject.voucherProject.repository.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.voucherProject.voucherProject.voucher.entity.FixedAmountVoucher;
import org.voucherProject.voucherProject.voucher.entity.PercentDiscountVoucher;
import org.voucherProject.voucherProject.voucher.entity.Voucher;
import org.voucherProject.voucherProject.voucher.repository.VoucherRepository;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VoucherRepositoryImplTest {

    @Autowired
    VoucherRepository voucherRepository;

    @BeforeEach
    void setup() {
        voucherRepository.deleteAll();
    }

    @Test
    public void saveAndFind() throws Exception {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1, UUID.randomUUID());
        Voucher saveVoucher = voucherRepository.save(voucher);

        UUID saveVoucherId = saveVoucher.getVoucherId();
        Voucher findVoucher = voucherRepository.findById(saveVoucherId).get();

        assertThat(findVoucher.getVoucherId()).isEqualTo(saveVoucher.getVoucherId());
    }

    @Test
//    @Disabled
    public void saveSame() throws Exception {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1, UUID.randomUUID());
        voucherRepository.save(voucher);

        assertThrows(RuntimeException.class, () -> voucherRepository.save(voucher));
    }

    @Test
//    @Disabled
    public void findAll() throws Exception {
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 3, UUID.randomUUID());
        Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 2, UUID.randomUUID());
        Voucher voucher3 = new FixedAmountVoucher(UUID.randomUUID(), 1, UUID.randomUUID());
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.save(voucher3);

        List<Voucher> findAllVoucher = voucherRepository.findAll();

        assertThat(findAllVoucher.size()).isEqualTo(3);
    }
}