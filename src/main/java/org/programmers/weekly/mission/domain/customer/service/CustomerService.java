package org.programmers.weekly.mission.domain.customer.service;

import org.programmers.weekly.mission.domain.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public <T> Iterable<T> getBlackList() {
        // 수정 필요
        return null;
    }
}
