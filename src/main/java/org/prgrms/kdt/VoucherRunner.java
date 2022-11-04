package org.prgrms.kdt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VoucherRunner implements CommandLineRunner {

    private final VoucherController voucherController;

    public VoucherRunner(VoucherController voucherController) {
        this.voucherController = voucherController;
    }

    @Override
    public void run(String... args) throws Exception {
        voucherController.execute();
    }
}

