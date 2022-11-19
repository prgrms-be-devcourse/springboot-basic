package org.prgrms.kdtspringdemo.domain.customer;

import org.prgrms.kdtspringdemo.domain.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


}
