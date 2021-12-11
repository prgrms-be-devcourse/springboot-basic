package org.programmers.kdt.customer.repository;

import org.programmers.kdt.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer insert(Customer customer);
    default void deleteCustomer(Customer customer) {
        deleteCustomer(customer.getCustomerId());
    }
    void deleteCustomer(UUID customerId);

    Optional<Customer> findById(UUID customerId);
    List<Customer> findByName(String name);
    Optional<Customer> findByEmail(String email);

    List<Customer> findAll();
    default Optional<Customer> updateName(Customer customer, String newName) {
        return updateName(customer.getCustomerId(), newName);
    }
    Optional<Customer> updateName(UUID customerId, String newName);

    Customer registerToBlacklist(Customer customer);
    List<Customer> findAllBlacklistCustomer();
    Optional<Customer> findCustomerOnBlacklistById(UUID customerId);
    default Optional<Customer> findCustomerOnBlacklistById(Customer customer) {
        return findCustomerOnBlacklistById(customer.getCustomerId());
    }

    void deleteAll();
}
