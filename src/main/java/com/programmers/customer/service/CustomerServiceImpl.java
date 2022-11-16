package com.programmers.customer.service;

import com.programmers.customer.Customer;
import com.programmers.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public void join(Customer customer) {
        Optional<Customer> findOne = customerRepository.findByEmail(customer.getEmail());

        if (findOne.isPresent()) {
            throw new RuntimeException("이미 사용중인 이메일입니다.");
        }

        customerRepository.insert(customer);
    }

    @Override
    @Transactional
    public Customer update(Customer customer) {
        return customerRepository.update(customer);
    }

    @Override
    public Customer findById(UUID customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }

    @Override
    public Customer findByName(String name) {
        return customerRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }

    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

}
