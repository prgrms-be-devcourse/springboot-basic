package com.voucher.vouchermanagement.service.customer;

import com.voucher.vouchermanagement.dto.customer.CustomerDto;
import com.voucher.vouchermanagement.repository.customer.CustomerRepository;

import java.util.List;
import java.util.UUID;

public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void join(CustomerJoinRequest joinRequest) {

    }

    public List<CustomerDto> findAll() {
        return null;
    }

    public CustomerDto findById(UUID id) {
        return null;
    }
}
