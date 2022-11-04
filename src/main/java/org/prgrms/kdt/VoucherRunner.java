package org.prgrms.kdt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VoucherRunner implements CommandLineRunner {

    private final VoucherController voucherAppManager;

    public VoucherRunner(VoucherController voucherAppManager) {
        this.voucherAppManager = voucherAppManager;
    }

    @Override
    public void run(String... args) throws Exception {
        voucherAppManager.execute();
    }
}

