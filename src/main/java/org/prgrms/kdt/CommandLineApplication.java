package org.prgrms.kdt;

import org.prgrms.kdt.config.VoucherConfig;
import org.prgrms.kdt.controller.VoucherController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
            VoucherConfig.class);
        VoucherController voucherProgram = ac.getBean(VoucherController.class);
        voucherProgram.run();
    }

}
