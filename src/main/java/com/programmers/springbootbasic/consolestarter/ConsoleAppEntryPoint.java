package com.programmers.springbootbasic.consolestarter;

import com.programmers.springbootbasic.configuration.RootAppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConsoleAppEntryPoint {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext
                = new AnnotationConfigApplicationContext(RootAppConfig.class);

        new CustomerVoucherConsoleApp
            (applicationContext.getBean(CustomerManager.class),
            applicationContext.getBean(VoucherManager.class)).run();

        applicationContext.close();
    }

}
