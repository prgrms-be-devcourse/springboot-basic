package com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    default boolean insert(Customer customer){
        return false;
    }

    default Optional<Customer> findById(UUID customerId){
        return Optional.empty();
    }

    default List<Customer> findAll() {
        return null;
    }

    default boolean update(Customer customer){
        return false;
    }

    default boolean delete(UUID customerId){
        return false;
    }

    List<Customer> findAllBlockCustomer();
}
