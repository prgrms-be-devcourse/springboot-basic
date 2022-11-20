package com.programmers.voucher.service;

import com.programmers.voucher.controller.dto.CustomerDto;
import com.programmers.voucher.model.customer.Customer;
import com.programmers.voucher.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer create(CustomerDto customerDto) {
        int customerId = customerRepository.save(customerDto);
        return new Customer(customerId, customerDto.customerName(), customerDto.email());
    }

    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Customer findByVoucher(UUID voucherId) {
        return customerRepository.findByVoucher(voucherId);
    }
}
