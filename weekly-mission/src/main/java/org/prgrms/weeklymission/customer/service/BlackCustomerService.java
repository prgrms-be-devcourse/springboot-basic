package org.prgrms.weeklymission.customer.service;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.weeklymission.console.Console;
import org.prgrms.weeklymission.customer.domain.Customer;
import org.prgrms.weeklymission.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    public Customer registerBlackCustomer(UUID customerId, String name) {
        var blackCustomer = Customer.blackCustomer(customerId, name);
        repository.save(blackCustomer);

        return blackCustomer;
    }

    public Customer findBlackCustomerById(UUID customerId) {
        return repository.findById(customerId)
                .orElseThrow(() -> new RuntimeException(NO_CUSTOMER.getMessage()));
    }

    public int countBlackCustomer() {
        return repository.countStorageSize();
    }

    public void printCreateBlackCustomer() {
        String name = console.takeInput();
        registerBlackCustomer(UUID.randomUUID(), name);
    }

    public void printAllBlackCustomers() throws RuntimeException {
        console.printData(checkCustomersAndReturn());
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
