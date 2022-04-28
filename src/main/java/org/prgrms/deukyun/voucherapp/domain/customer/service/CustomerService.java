package org.prgrms.deukyun.voucherapp.domain.customer.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.deukyun.voucherapp.domain.customer.domain.Customer;
import org.prgrms.deukyun.voucherapp.domain.customer.domain.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<Customer> findAllBlocked(){
        return customerRepository.findAllBlocked();
    }

    public void insert(Customer customer) {
        customerRepository.insert(customer);
    }
}
