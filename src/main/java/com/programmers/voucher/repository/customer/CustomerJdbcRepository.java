package com.programmers.voucher.repository.customer;

import com.programmers.voucher.controller.dto.CustomerDto;
import com.programmers.voucher.model.customer.Customer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {
    private static final String insertSql
            = "INSERT INTO customers(customer_name, email) " +
            "VALUES(:customerName, :email)";
    private static final String findSql
            = "SELECT * FROM customers WHERE email = :email";

    private static final RowMapper<Customer> rowMapper = (resultSet, count) -> {
        int customerId = resultSet.getInt("customer_id");
        String customerName = resultSet.getString("customer_name");
        String email = resultSet.getString("email");
        return new Customer(customerId, customerName, email);
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(CustomerDto customerDto) {
        return jdbcTemplate.update(insertSql, toParamMap(customerDto), new GeneratedKeyHolder());
    }

    @Override
    public Customer findByEmail(String email) {
        return jdbcTemplate.queryForObject(findSql, toEmailMap(email), rowMapper);
    }

    private SqlParameterSource toParamMap(CustomerDto customerDto) {
        return new MapSqlParameterSource()
                .addValue("customerName", customerDto.customerName())
                .addValue("email", customerDto.email());
    }

    private SqlParameterSource toEmailMap(String email) {
        return new MapSqlParameterSource()
                .addValue("email", email);
    }
}
