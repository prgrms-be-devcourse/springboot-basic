package com.programmers.springweekly.repository.customer;

import com.programmers.springweekly.domain.customer.Customer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer save(Customer customer);

    void update(Customer customer);

    Optional<Customer> findById(UUID customerId);

    List<Customer> findAll();

    List<Customer> getBlackList();

    int deleteById(UUID customerId);

    void deleteAll();

    boolean existById(UUID customerId);

}
