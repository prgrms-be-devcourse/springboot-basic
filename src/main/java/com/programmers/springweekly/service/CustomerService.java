package com.programmers.springweekly.service;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.repository.customer.CustomerRepository;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Map<UUID, Customer> getBlackList() {
        return customerRepository.getBlackList();
    }
}
