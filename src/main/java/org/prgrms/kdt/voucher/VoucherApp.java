package org.prgrms.kdt.voucher;

import org.prgrms.kdt.voucher.controller.Controller;
import org.prgrms.kdt.voucher.repository.MemoryVoucherRepository;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.prgrms.kdt.voucher.view.ConsoleInput;
import org.prgrms.kdt.voucher.view.ConsoleOutput;

public class VoucherApp {

    public static void main(String[] args) {
        new Controller(new ConsoleInput(), new ConsoleOutput(), new VoucherService(new MemoryVoucherRepository())).run();
    }
}
