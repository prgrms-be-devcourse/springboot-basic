package com.programmers.springbootbasic;

import com.programmers.springbootbasic.configuration.VoucherAppConfiguration;
import com.programmers.springbootbasic.service.VoucherManagementApp;
import com.programmers.springbootbasic.service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppEntryPoint {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext
                = new AnnotationConfigApplicationContext(VoucherAppConfiguration.class);

        new VoucherManagementApp
                (applicationContext.getBean(VoucherService.class)).run();

        applicationContext.close();
    }

}
