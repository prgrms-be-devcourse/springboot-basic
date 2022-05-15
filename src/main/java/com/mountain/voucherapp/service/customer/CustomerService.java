package com.mountain.voucherapp.service.customer;

import com.mountain.voucherapp.dao.customer.CustomerRepository;
import com.mountain.voucherapp.dto.CustomerDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerDto> findAll() {
        return customerRepository.findAll();
    }
}
