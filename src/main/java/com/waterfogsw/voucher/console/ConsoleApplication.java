package com.waterfogsw.voucher.console;

import com.waterfogsw.voucher.member.controller.MemberController;
import com.waterfogsw.voucher.voucher.controller.VoucherController;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class ConsoleApplication implements ApplicationRunner {
    private final Input input;
    private final Output output;
    private final VoucherController voucherController;
    private final MemberController memberController;

    public ConsoleApplication(Input input, Output output, VoucherController voucherController, MemberController memberController) {
        this.input = input;
        this.output = output;
        this.voucherController = voucherController;
        this.memberController = memberController;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
