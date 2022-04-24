package com.voucher.vouchermanagement.service.customer;

import com.voucher.vouchermanagement.dto.customer.CustomerDto;
import com.voucher.vouchermanagement.dto.customer.CustomerJoinResponse;
import com.voucher.vouchermanagement.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerJoinResponse join(String name, String email) {
        return null;
    }

    public List<CustomerDto> findAll() {
        return null;
    }

    public CustomerDto findById(UUID id) {
        return null;
    }
}
