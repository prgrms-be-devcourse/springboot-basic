package org.prgms.vouchermanagement.customer.service;

import org.prgms.vouchermanagement.customer.domain.entity.Customer;
import org.prgms.vouchermanagement.customer.domain.repository.CustomerRepository;
import org.prgms.vouchermanagement.global.constant.ExceptionMessageConstant;
import org.prgms.vouchermanagement.global.io.Console;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.List;

@Service
public class CustomerService implements ApplicationContextAware {

    private final CustomerRepository customerRepository;
    private final Console console;
    private ApplicationContext applicationContext;

    public CustomerService(CustomerRepository customerRepository, Console console) {
        this.customerRepository = customerRepository;
        this.console = console;
    }

    public void showBlackList() throws NoSuchFileException {
        Resource resource = applicationContext.getResource("customer_blacklist.csv");
        try {
            console.printCustomerBlackList(resource.getFile().toPath().toString());
        } catch (IOException e){
            throw new NoSuchFileException(ExceptionMessageConstant.NO_BLACK_LIST_FILE_EXCEPTION);
        }
    }

    public void showCustomerList() {
        List<Customer> customers = customerRepository.findAll();
        console.printCustomerList(customers);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
