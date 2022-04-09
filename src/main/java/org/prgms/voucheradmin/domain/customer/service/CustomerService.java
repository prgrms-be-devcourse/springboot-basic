package org.prgms.voucheradmin.domain.customer.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.prgms.voucheradmin.domain.customer.dao.CustomerRepository;
import org.prgms.voucheradmin.domain.customer.dto.CustomerDto;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerDto> getBlackList() throws IOException {
        List<CustomerDto> blackListedCustomers= customerRepository.getAll().stream()
                .map(customer -> new CustomerDto(customer.getId(), customer.getName()))
                .collect(Collectors.toList());
        return blackListedCustomers;
    }
}
