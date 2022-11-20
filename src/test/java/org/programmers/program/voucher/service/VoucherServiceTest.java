package org.programmers.program.voucher.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.programmers.program.voucher.model.FixedAmountVoucher;
import org.programmers.program.voucher.model.Voucher;
import org.programmers.program.voucher.repository.MemoryVoucherRepository;
import org.programmers.program.voucher.repository.VoucherRepository;

import java.util.UUID;


class VoucherServiceTest {
    VoucherRepository repository = new MemoryVoucherRepository();
    VoucherService service = new VoucherService(repository);

    @Test
    @DisplayName("바우처 생성 테스트")
    void createTest(){
        Voucher v1 = new FixedAmountVoucher(UUID.randomUUID(), 40L);
    }
}