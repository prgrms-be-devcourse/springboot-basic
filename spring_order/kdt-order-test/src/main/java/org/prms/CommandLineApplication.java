package org.prms;

import org.prms.configure.AppConfiguration;
import org.prms.controller.cmdController;
import org.prms.service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication {
    public static void main(String[] args) {

        var applicationContext=new AnnotationConfigApplicationContext(AppConfiguration.class);
        VoucherService voucherService = applicationContext.getBean(VoucherService.class);

        cmdController controller=new cmdController(voucherService);
        controller.run();

    }

}
