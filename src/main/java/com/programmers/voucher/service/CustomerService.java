package com.programmers.voucher.service;

import com.programmers.voucher.controller.customer.dto.CustomerCreateRequest;
import com.programmers.voucher.controller.customer.dto.CustomerResponse;
import com.programmers.voucher.controller.customer.dto.CustomerUpdateRequest;
import com.programmers.voucher.entity.customer.Customer;
import com.programmers.voucher.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerResponse createCustomer(CustomerCreateRequest request) {
        checkDuplicated(customerRepository.findByNickname(request.nickname()));
        Customer customer = customerRepository.insert(request.toEntity());

        return CustomerResponse.from(customer);
    }

    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerResponse::from)
                .toList();
    }

    public CustomerResponse getCustomer(UUID customerId) {
        Customer customer = checkExisted(customerRepository.findById(customerId));
        return CustomerResponse.from(customer);
    }

    @Transactional
    public CustomerResponse updateCustomer(UUID customerId, CustomerUpdateRequest request) {
        Customer customer = checkExisted(customerRepository.findById(customerId));
        customer.update(request.nickname());

        return CustomerResponse.from(customerRepository.update(customer));
    }

    @Transactional
    public void deleteCustomer(UUID customerId) {
        customerRepository.delete(customerId);
    }

    private void checkDuplicated(Optional<Customer> customer) {
        if (customer.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
    }

    private Customer checkExisted(Optional<Customer> customer) {
        return customer.orElseThrow(() -> new IllegalArgumentException("존재하는 고객이 없습니다."));
    }
}
