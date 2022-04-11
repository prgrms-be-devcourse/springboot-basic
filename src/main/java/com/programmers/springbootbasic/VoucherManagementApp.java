package com.programmers.springbootbasic;

import com.programmers.springbootbasic.configuration.VoucherAppConfiguration;
import com.programmers.springbootbasic.service.VoucherManager;
import com.programmers.springbootbasic.service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class VoucherManagementApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext
                = new AnnotationConfigApplicationContext(VoucherAppConfiguration.class);

        new VoucherManager
                (applicationContext.getBean(VoucherService.class)).run();

        applicationContext.close();
    }

}
