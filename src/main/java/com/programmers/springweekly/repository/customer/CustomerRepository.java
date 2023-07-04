package com.programmers.springweekly.repository.customer;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.dto.CustomerUpdateDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer save(Customer customer);

    Customer update(UUID customerId, CustomerUpdateDto customerUpdateDto);

    Optional<Customer> findById(UUID customerId);

    List<Customer> findAll();

    List<Customer> getBlackList();

    void deleteById(UUID customerId);

    void deleteAll();

}
