package org.prgrms.springbootbasic;

import org.prgrms.springbootbasic.controller.VoucherController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class VoucherApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
            VoucherConfig.class);
        context.getBean(VoucherController.class).run();
        context.close();
    }
}
