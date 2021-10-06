package org.prgrms.kdt.customer.service;

import org.prgrms.kdt.customer.domain.BannedCustomer;
import org.prgrms.kdt.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<BannedCustomer> getBlackList() {
        try {
            return customerRepository.getBlackListCSV();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}