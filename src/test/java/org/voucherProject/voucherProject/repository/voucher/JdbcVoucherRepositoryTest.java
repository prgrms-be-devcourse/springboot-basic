package org.voucherProject.voucherProject.repository.voucher;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.voucherProject.voucherProject.voucher.entity.FixedAmountVoucher;
import org.voucherProject.voucherProject.voucher.entity.VoucherStatus;
import org.voucherProject.voucherProject.voucher.repository.VoucherRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
class JdbcVoucherRepositoryTest {

    @Autowired
    VoucherRepository voucherRepository;

    @Test
    public void save() throws Exception {

        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10, VoucherStatus.VALID, LocalDateTime.now());
        voucherRepository.save(fixedAmountVoucher);
        //given

        //when

        //then

    }
}