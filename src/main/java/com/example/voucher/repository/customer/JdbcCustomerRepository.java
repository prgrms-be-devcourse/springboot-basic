package com.example.voucher.repository.customer;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import com.example.voucher.domain.customer.Customer;
import com.example.voucher.repository.QueryBuilder;

@Component
public class JdbcCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer save(Customer customer) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("customerId", customer.getCustomerId().toString())
            .addValue("name", customer.getName())
            .addValue("email", customer.getEmail())
            .addValue("customerType", customer.getCustomerType().toString())
            .addValue("createdAt", customer.getCreatedAt());

        String sql = new QueryBuilder().insertInto("CUSTOMER")
            .values("customerId", "name", "email", "customerType", "createdAt")
            .build();

        jdbcTemplate.update(sql, parameterSource);

        return customer;
    }

}
