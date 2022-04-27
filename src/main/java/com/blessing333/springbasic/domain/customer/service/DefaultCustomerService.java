package com.blessing333.springbasic.domain.customer.service;

import com.blessing333.springbasic.domain.customer.model.Customer;
import com.blessing333.springbasic.domain.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DefaultCustomerService implements CustomerService{
    private final CustomerRepository repository;

    @Transactional
    @Override
    public Customer registerCustomer(Customer customer) {
        customer.generateCustomerId();
        customer.renewCreatedDate();
        return repository.insert(customer);
    }

    @Override
    public Customer inquiryCustomerInformation(UUID id) {
        return repository.findById(id).orElseThrow(()->new IllegalArgumentException("존재하지 않는 고객 ID 입니다"));
    }

    @Override
    public List<Customer> loadAllCustomerInformation() {
        return repository.findAll();
    }
}
