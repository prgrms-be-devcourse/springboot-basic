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
        return customerRepository.save(customerDto);
    }

    public Customer findByVoucher(UUID voucherId) {
        return customerRepository.findByVoucher(voucherId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 바우처입니다."));
    }
}

