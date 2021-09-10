package org.prgrms.kdtspringdemo.console;

import org.prgrms.kdtspringdemo.configuration.AppConfiguration;
import org.prgrms.kdtspringdemo.customer.Customer;
import org.prgrms.kdtspringdemo.customer.service.CustomerService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.stream.Stream;

public class CustomerOperator implements CommandOperator<Customer> {
    private final CustomerService customerService;

    public CustomerOperator() {
        var application = new AnnotationConfigApplicationContext(AppConfiguration.class);
        customerService = application.getBean(CustomerService.class);
    }

    @Override
    public Customer create(String[] splitList) {
        //None
        return null;
    }

    @Override
    public List<Customer> getAllitems() {
        return customerService.getAllCustomers();
    }

    public List<Customer> getAllBlacklist() {
        return customerService.getAllBlacklist();
    }
}
