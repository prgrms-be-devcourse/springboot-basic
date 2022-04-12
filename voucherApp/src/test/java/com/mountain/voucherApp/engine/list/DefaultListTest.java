package com.mountain.voucherApp.engine.list;

import com.mountain.voucherApp.engine.exit.DefaultExit;
import com.mountain.voucherApp.io.Console;
import com.mountain.voucherApp.repository.MemoryVoucherRepository;
import com.mountain.voucherApp.repository.VoucherRepository;
import com.mountain.voucherApp.voucher.FixedAmountVoucher;
import com.mountain.voucherApp.voucher.Voucher;
import com.mountain.voucherApp.voucher.VoucherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;

import static org.junit.jupiter.api.Assertions.*;

class DefaultListTest {
    @Test
    @Description("list 기능 정상작동 확인.")
    void testExit() {
        VoucherRepository voucherRepository = new MemoryVoucherRepository();
        for (int i = 1; i <= 5; i++) {
            voucherRepository.insert(new FixedAmountVoucher(917));
        }
        VoucherService voucherService = new VoucherService(voucherRepository);
        Console console = new Console();
        ListStrategy defaultList = new DefaultList(console, voucherService);
        Assertions.assertDoesNotThrow(() -> defaultList.list());
        console.close();
    }
}