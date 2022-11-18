package org.prgrms.kdt.customer;

import java.util.List;
import java.util.Optional;

public class JdbcCustomerManager implements CustomerManager {

    @Override
    public void save(Customer customer) {

    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Optional<Customer> findById(long id) {
        return Optional.empty();
    }

    @Override
    public void update(Customer customer) {

    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public void deleteAll() {

    }
}
