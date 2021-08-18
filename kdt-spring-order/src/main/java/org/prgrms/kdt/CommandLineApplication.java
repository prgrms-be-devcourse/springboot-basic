package org.prgrms.kdt;

import org.prgrms.kdt.voucher.controller.VoucherController;
import org.prgrms.kdt.voucher.io.Console;

public class CommandLineApplication {
    public static void main(String[] args) {
        VoucherController controller = new VoucherController();
        controller.run();
    }
}
