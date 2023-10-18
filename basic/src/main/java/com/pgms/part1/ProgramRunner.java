package com.pgms.part1;

import com.pgms.part1.domain.voucher.controller.VoucherController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProgramRunner implements CommandLineRunner {
    private final VoucherController voucherController;

    public ProgramRunner(VoucherController voucherController) {
        this.voucherController = voucherController;
    }

    @Override
    public void run(String... args) throws Exception {
        voucherController.init();
    }
}
