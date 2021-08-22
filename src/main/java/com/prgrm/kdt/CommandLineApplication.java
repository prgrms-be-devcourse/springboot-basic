package com.prgrm.kdt;

import com.prgrm.kdt.voucher.application.VoucherService;
import com.prgrm.kdt.voucher.presentation.VoucherController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        VoucherService voucherService = applicationContext.getBean(VoucherService.class);
        new VoucherController(voucherService).run();
    }
}
