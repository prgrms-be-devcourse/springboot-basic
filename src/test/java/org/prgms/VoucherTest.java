package org.prgms;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.management.voucher.entity.FixedAmountVoucher;
import org.prgms.management.voucher.entity.PercentAmountVoucher;
import org.prgms.management.voucher.entity.Voucher;
import org.prgms.management.voucher.repository.VoucherMemoryRepository;
import org.prgms.management.voucher.repository.VoucherRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class VoucherTest {
    @Test
    @DisplayName("바우처 등록 테스트")
    void addVoucherTest() {
        UUID uuid = UUID.randomUUID();
        Voucher voucher = FixedAmountVoucher.getFixedAmountVoucher(uuid, 100,
                "fixedAmountVoucher", "FixedAmountVoucher");
        assertEquals(uuid, voucher.getVoucherId());

        uuid = UUID.randomUUID();
        voucher = PercentAmountVoucher.getPercentAmountVoucher(uuid, 10,
                "percentAmountVoucher", "PercentAmountVoucher");
        assertEquals(uuid, voucher.getVoucherId());
    }

    @Test
    @DisplayName("바우처 목록 조회 테스트")
    void getListOfVoucherTest() {
        VoucherRepository voucherRepository = new VoucherMemoryRepository();
        voucherRepository.save(
                FixedAmountVoucher.getFixedAmountVoucher(UUID.randomUUID(), 100,
                        "fixedAmountVoucher", "PercentAmountVoucher"));
        voucherRepository.save(
                PercentAmountVoucher.getPercentAmountVoucher(UUID.randomUUID(), 10,
                        "percentAmountVoucher", "PercentAmountVoucher"));

        var voucherMap = voucherRepository.getAll();
        assertEquals(2, voucherMap.size());
    }
}
