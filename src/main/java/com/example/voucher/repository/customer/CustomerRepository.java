package com.example.voucher.repository.customer;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import com.example.voucher.domain.customer.Customer;

@Repository
public interface CustomerRepository {

    Customer save(Customer customer);

    List<Customer> findAll();

    Customer findById(UUID customerID);

    void deleteAll();

    void deleteById(UUID customerID);

    Customer update(Customer customer);
}

