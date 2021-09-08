package org.prgrms.dev;

import org.prgrms.dev.config.AppConfiguration;
import org.prgrms.dev.customer.service.CustomerService;
import org.prgrms.dev.io.OutputConsole;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BlackListApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        CustomerService customerService = applicationContext.getBean(CustomerService.class);
        OutputConsole outputConsole = new OutputConsole();
        outputConsole.printBlackList(customerService.blackList());
    }
}