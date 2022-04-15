package org.voucherProject.voucherProject.repository.customer;

import org.voucherProject.voucherProject.entity.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByName(String customerName);

    Optional<Customer> findByEmail(String customerEmail);

    List<Customer> findAll();

    Customer save(Customer customer);

    void deleteAll();


}
