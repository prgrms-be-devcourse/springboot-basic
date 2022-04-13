package org.voucherProject.voucherProject.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.voucherProject.voucherProject.entity.voucher.FixedAmountVoucher;
import org.voucherProject.voucherProject.entity.voucher.PercentDiscountVoucher;
import org.voucherProject.voucherProject.entity.voucher.Voucher;
import org.voucherProject.voucherProject.repository.voucher.FileVoucherRepository;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class VoucherRepositoryFileImplTest {

    @Autowired
    FileVoucherRepository voucherRepoFile;

    @Test
    public void saveAndFindById() throws Exception {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 3);
        Voucher saveVoucher = voucherRepoFile.save(voucher);

        UUID voucherId = saveVoucher.getVoucherId();

        Voucher findVoucher = voucherRepoFile.findById(voucherId).get();

        assertThat(findVoucher.getVoucherId()).isEqualTo(voucherId);
    }

    @Test
    public void findAll() throws Exception {
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 3);
        Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 2);
        Voucher voucher3 = new FixedAmountVoucher(UUID.randomUUID(), 1);
        voucherRepoFile.save(voucher1);
        voucherRepoFile.save(voucher2);
        voucherRepoFile.save(voucher3);

        List<Voucher> findAllVoucher = voucherRepoFile.findAll();

        boolean result1 = findAllVoucher.stream().anyMatch(v -> v.getVoucherId().equals(voucher1.getVoucherId()));
        boolean result2 = findAllVoucher.stream().anyMatch(v -> v.getVoucherId().equals(voucher2.getVoucherId()));
        boolean result3 = findAllVoucher.stream().anyMatch(v -> v.getVoucherId().equals(voucher3.getVoucherId()));

        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
        assertThat(result3).isTrue();
    }

    @Test
    public void saveSameVoucherId() throws Exception {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 3);
        voucherRepoFile.save(voucher);
        Assertions.assertThrows(RuntimeException.class, () -> voucherRepoFile.save(voucher));
    }

}