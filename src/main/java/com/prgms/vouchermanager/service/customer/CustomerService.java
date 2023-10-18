package com.prgms.vouchermanager.service.customer;

import com.prgms.vouchermanager.domain.customer.Customer;
import com.prgms.vouchermanager.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getBl성ackList() {
        return customerRepository.getBlackList();
    }
}
