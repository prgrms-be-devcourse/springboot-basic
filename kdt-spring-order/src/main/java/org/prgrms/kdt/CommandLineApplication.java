package org.prgrms.kdt;

import org.prgrms.kdt.controller.VoucherController;
import org.prgrms.kdt.domain.InputType;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"org.prgrms.kdt.controller",
        "org.prgrms.kdt.io",
        "org.prgrms.kdt.repository",
        "org.prgrms.kdt.service",
        "org.prgrms.kdt.fileIO"
})
public class CommandLineApplication {

    private static InputType inputType;
    public static void main(String[] args) {


        VoucherController voucherController = new VoucherController();
        voucherController.run(new AnnotationConfigApplicationContext(CommandLineApplication.class));

    }
}
