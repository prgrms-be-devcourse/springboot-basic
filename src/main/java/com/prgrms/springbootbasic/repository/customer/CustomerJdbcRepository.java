package com.prgrms.springbootbasic.repository.customer;

import com.prgrms.springbootbasic.domain.customer.Customer;
import java.util.List;

public class CustomerJdbcRepository implements CustomerRepository {

    @Override
    public Customer insert(Customer customer) {
        return null;
    }

    @Override
    public void update(Customer customer) {

    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
