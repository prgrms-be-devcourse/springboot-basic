package org.prgms.voucherProgram.service;

import java.util.List;

import org.prgms.voucherProgram.entity.customer.Customer;
import org.prgms.voucherProgram.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository userRepository) {
        this.customerRepository = userRepository;
    }

    public List<Customer> findBlackList() {
        return customerRepository.findBlackCustomers();
    }
}
