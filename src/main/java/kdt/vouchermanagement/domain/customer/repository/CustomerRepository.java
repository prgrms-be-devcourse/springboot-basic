package kdt.vouchermanagement.domain.customer.repository;

import kdt.vouchermanagement.domain.customer.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    void save(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(long id);

    void deleteById(long id);
}
