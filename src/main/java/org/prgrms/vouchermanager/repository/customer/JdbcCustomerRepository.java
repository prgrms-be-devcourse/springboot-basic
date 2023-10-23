package org.prgrms.vouchermanager.repository.customer;

import org.prgrms.vouchermanager.domain.customer.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcCustomerRepository implements CustomerRepositroy{
    private JdbcTemplate jdbcTemplate;
    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Customer save(Customer customer) {
        return null;
    }
}
