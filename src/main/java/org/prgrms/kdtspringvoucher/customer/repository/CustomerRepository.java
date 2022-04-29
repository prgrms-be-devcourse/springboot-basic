package org.prgrms.kdtspringvoucher.customer.repository;

import org.prgrms.kdtspringvoucher.customer.entity.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer insert(Customer customer);
    Customer update(Customer customer);
    List<Customer> findAll();
    List<Customer> findBlackCustomers();
    List<Customer> findCustomerByVoucher(UUID voucherId);
    Optional<Customer> findById(UUID customerId);
    Optional<Customer> findByName(String name);
    Optional<Customer> findByEmail(String email);
}
