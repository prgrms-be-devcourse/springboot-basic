package com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;


@Repository
@Profile("release")
public class JdbcCustomerRepository implements CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean insert(UUID customerId, String name) {
        final String sql = "INSERT INTO customers (customer_id, name) VALUES (?,?)";
        return jdbcTemplate.update(sql,
                customerId.toString(),
                name) == 1;

    }

    @Override
    public Customer findById(UUID customerId) {
        final String sql = "SELECT * FROM customers WHERE customer_id = ?";
        return jdbcTemplate.queryForObject(sql, customerRowMapper, customerId.toString());
    }

    @Override
    public List<Customer> findAll() {
        final String sql = "SELECT * FROM customers";
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public boolean update(UUID customerId, String name) {
        final String sql = "UPDATE customers SET name = ? WHERE customer_id = ?";
        return jdbcTemplate.update(sql, name, customerId.toString()) == 1;
    }

    @Override
    public boolean delete(UUID customerId) {
        final String sql = "SELECT * FROM customers WHERE customer_id = ?";
        return jdbcTemplate.update(sql, customerId.toString()) == 1;
    }

    private final RowMapper<Customer> customerRowMapper =
            (rs, rowNum) -> new Customer(
                    UUID.fromString(rs.getString("customer_id")),
                    rs.getString("name")
            );
}


