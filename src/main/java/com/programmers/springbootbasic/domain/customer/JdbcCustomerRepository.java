package com.programmers.springbootbasic.domain.customer;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {
    // TODO:
    //  5차에 구현 예정
    @Override
    public Optional<Customer> save(Customer customer) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> update(Customer customer) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return Optional.empty();
    }
}
