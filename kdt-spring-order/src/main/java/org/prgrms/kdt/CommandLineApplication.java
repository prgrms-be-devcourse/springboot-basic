package org.prgrms.kdt;

import org.prgrms.kdt.config.AppConfiguration;
import org.prgrms.kdt.controller.VoucherController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication{
    public static void main(String[] args){
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        applicationContext.getBean(VoucherController.class).start();
    }
}
