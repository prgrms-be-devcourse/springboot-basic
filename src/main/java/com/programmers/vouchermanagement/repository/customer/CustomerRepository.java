package com.programmers.vouchermanagement.repository.customer;

import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.dto.customer.GetCustomersRequestDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    void save(Customer customer);
    void saveAll(List<Customer> customers);
    Optional<Customer> findById(UUID id);
    List<Customer> findAll(GetCustomersRequestDto request);
    void deleteAll();
}
