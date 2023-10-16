package org.prgrms.kdtspringdemo.customer.service;

import org.prgrms.kdtspringdemo.customer.domain.Customer;
import org.prgrms.kdtspringdemo.customer.repository.CustomerRepository;
import org.prgrms.kdtspringdemo.customer.repository.FileCustomerRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService() {
        this.customerRepository = new FileCustomerRepository();
    }

    public Map<UUID, Customer> getBlackListCustomers() throws IOException {
        return customerRepository.getAllBlackList().get();
    }
}
