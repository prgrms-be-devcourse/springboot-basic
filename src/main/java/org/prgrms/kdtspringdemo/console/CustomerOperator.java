package org.prgrms.kdtspringdemo.console;

import org.prgrms.kdtspringdemo.configuration.AppConfiguration;
import org.prgrms.kdtspringdemo.customer.Customer;
import org.prgrms.kdtspringdemo.customer.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class CustomerOperator implements CommandOperator<Customer> {
    private final CustomerService customerService;

    public CustomerOperator() {
        var application = new AnnotationConfigApplicationContext(AppConfiguration.class);
        customerService = application.getBean(CustomerService.class);
    }

    @Override
    public boolean create(String[] splitList) {
        if (!validationCheck(splitList)) return false;
        Customer customer = customerService.saveCustomer(splitList[0], splitList[1], splitList[2]);
        return customer != null;
    }

    @Override
    public List<Customer> getAllitems() {
        return customerService.getAllCustomers();
    }

    public List<Customer> getAllBlacklist() {
        return customerService.getAllBlacklist();
    }

    public boolean validationCheck(String[] splitList) {
        if (splitList.length != 3) return false;
        if (!splitList[2].equals("NORMAL") && !splitList[2].equals("BLACK")) return false;

        return true;
    }
}
