package org.prgrms.kdt.customer.service;


import org.prgrms.kdt.customer.domain.Customer;
import org.prgrms.kdt.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final String FILE_PATH = "src/main/resources/customer_blacklist.csv";

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getBlackList() {
        return customerRepository.readFile(FILE_PATH);
    }
}
