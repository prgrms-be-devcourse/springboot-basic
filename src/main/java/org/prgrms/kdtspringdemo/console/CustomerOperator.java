package org.prgrms.kdtspringdemo.console;

import org.prgrms.kdtspringdemo.configuration.AppConfiguration;
import org.prgrms.kdtspringdemo.customer.CustomerService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CustomerOperator implements CommandOperator {
    private final CustomerService customerService;

    public CustomerOperator() {
        var application = new AnnotationConfigApplicationContext(AppConfiguration.class);
        customerService = application.getBean(CustomerService.class);
    }

    @Override
    public void create(String[] splitList) {
        //None
    }

    @Override
    public void printAll() {
        customerService.printAll();
    }

    public void printBlacklist() {
        customerService.printBlacklist();
    }
}
