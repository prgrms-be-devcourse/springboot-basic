package org.prgrms.vouchermanager.customer.repository;

import org.prgrms.vouchermanager.customer.domain.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CustomerJDBCRepository implements CustomerRepository {
    @Override
    public Customer insert(Customer customer) {
        return null;
    }

    @Override
    public Customer update(Customer customer) {
        return null;
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findByName(UUID customerId) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findByEmail(UUID customerId) {
        return Optional.empty();
    }

    @Override
    public void delete(UUID customerId) {

    }

    @Override
    public void deleteAll() {

    }
}
