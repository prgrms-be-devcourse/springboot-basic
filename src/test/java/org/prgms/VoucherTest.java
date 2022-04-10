package org.prgms;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.management.entity.FixedAmountVoucher;
import org.prgms.management.entity.PercentAmountVoucher;
import org.prgms.management.entity.Voucher;
import org.prgms.management.repository.VoucherMemoryRepository;
import org.prgms.management.repository.VoucherRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class VoucherTest {
    @Test
    @DisplayName("바우처 등록 테스트")
    void addVoucherTest() {
        UUID uuid = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(uuid, 100L,
                "fixedAmountVoucher", "FixedAmountVoucher");
        assertEquals(uuid, voucher.getVoucherId());

        uuid = UUID.randomUUID();
        voucher = new PercentAmountVoucher(uuid, 10L,
                "percentAmountVoucher", "PercentAmountVoucher");
        assertEquals(uuid, voucher.getVoucherId());
    }

    @Test
    @DisplayName("바우처 목록 조회 테스트")
    void getListOfVoucherTest() {
        VoucherRepository voucherRepository = new VoucherMemoryRepository();
        voucherRepository.save(
                new FixedAmountVoucher(UUID.randomUUID(), 100L,
                        "fixedAmountVoucher", "PercentAmountVoucher"));
        voucherRepository.save(
                new PercentAmountVoucher(UUID.randomUUID(), 10L,
                        "percentAmountVoucher", "PercentAmountVoucher"));

        var voucherMap = voucherRepository.getAll();
        assertEquals(2, voucherMap.size());
    }
}
