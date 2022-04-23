package com.voucher.vouchermanagement.repository.customer;

import com.voucher.vouchermanagement.model.customer.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {
    @Override
    public void insert(Customer voucher) {

    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
