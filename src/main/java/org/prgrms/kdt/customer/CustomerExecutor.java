package org.prgrms.kdt.customer;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerExecutor {

    private final BlackCustomerManager blackCustomerManager;

    public CustomerExecutor(BlackCustomerManager blackCustomerManager) {
        this.blackCustomerManager = blackCustomerManager;
    }

    public List<Customer> blacklist() {
        return blackCustomerManager.findAll();
    }
}
