package org.prgrms.weeklymission.customer.service;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.weeklymission.console.Console;
import org.prgrms.weeklymission.customer.domain.Customer;
import org.prgrms.weeklymission.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.prgrms.weeklymission.utils.ErrorMessage.NO_CUSTOMER;

@Service
@Profile(value = {"local", "dev"})
@Slf4j
public class BlackCustomerService {
    private final CustomerRepository repository;
    private final Console console;

    @Autowired
    public BlackCustomerService(CustomerRepository repository, Console console) {
        this.repository = repository;
        this.console = console;
    }

    public Customer findBlackCustomerById(String customerId) {
        return repository.findById(customerId)
                .orElseThrow(() -> new RuntimeException(NO_CUSTOMER.getMessage()));
    }

    public void printCreateBlackCustomer() {
        String[] idAndName = console.inputForRegisterCustomer();
        registerBlackCustomer(idAndName[0], idAndName[1]);
        console.saveSuccessMessage();
    }

    public Customer registerBlackCustomer(String customerId, String name) {
        var blackCustomer = Customer.blackCustomer(customerId, name);
        repository.save(blackCustomer);

        return blackCustomer;
    }

    public void printAllBlackCustomers() throws RuntimeException {
        StringBuilder sb = new StringBuilder();
        checkCustomersAndReturn().forEach(c -> sb.append(c.toString()).append("\n"));
        console.printData(sb.toString());
    }

    public int getBlackCustomerCount() {
        return repository.getStorageSize();
    }

    public void deleteData() {
        repository.clear();
    }


    private List<Customer> checkCustomersAndReturn() throws RuntimeException {
        List<Customer> blackCustomers = repository.findAll();
        if(blackCustomers.isEmpty()) {
            log.error("repository is empty");
            throw new RuntimeException(NO_CUSTOMER.getMessage());
        }

        return blackCustomers;
    }
}
