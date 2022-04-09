package org.voucherProject.voucherProject.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.voucherProject.voucherProject.entity.voucher.FixedAmountVoucher;
import org.voucherProject.voucherProject.entity.voucher.PercentDiscountVoucher;
import org.voucherProject.voucherProject.entity.voucher.Voucher;
import org.voucherProject.voucherProject.service.voucher.VoucherService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VoucherServiceImplTest {

    @Autowired
    VoucherService voucherService;

    @Test
    public void saveAndGet() throws Exception {

        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1);
        Voucher saveVoucher = voucherService.save(voucher);


        Voucher getVoucher = voucherService.getVoucher(saveVoucher.getVoucherId());
        assertThat(saveVoucher).isEqualTo(getVoucher);
    }

    @Test
    public void saveSame() throws Exception {

        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1);
        voucherService.save(voucher);

        assertThrows(IOException.class, () -> voucherService.save(voucher));
    }

    @Test
    public void findNull() throws Exception {

        assertThrows(IllegalArgumentException.class, () -> voucherService.getVoucher(UUID.randomUUID()));

    }

    @Test
    public void findAll() throws Exception {

        Voucher saveVoucher1 = voucherService.save(new FixedAmountVoucher(UUID.randomUUID(), 11));
        Voucher saveVoucher2 = voucherService.save(new FixedAmountVoucher(UUID.randomUUID(), 12));
        Voucher saveVoucher3 = voucherService.save(new PercentDiscountVoucher(UUID.randomUUID(), 13));
        Voucher saveVoucher4 = voucherService.save(new PercentDiscountVoucher(UUID.randomUUID(), 14));

        List<Voucher> findAllVoucher = voucherService.findAll();
        assertThat(findAllVoucher.contains(saveVoucher1)).isTrue();
        assertThat(findAllVoucher.contains(saveVoucher2)).isTrue();
        assertThat(findAllVoucher.contains(saveVoucher3)).isTrue();
        assertThat(findAllVoucher.contains(saveVoucher4)).isTrue();
    }
}