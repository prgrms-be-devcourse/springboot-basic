package com.mountain.voucherApp.service.customer;

import com.mountain.voucherApp.dao.customer.CustomerRepository;
import com.mountain.voucherApp.dto.CustomerDto;
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
