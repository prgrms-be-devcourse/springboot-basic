package com.programmers.voucher.repository.customer;

import com.programmers.voucher.dto.CustomerDto;
import com.programmers.voucher.model.customer.Customer;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {
    private static final String insertSql
            = "INSERT INTO customers(customer_name, email) " +
            "VALUES(:customerName, :email)";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(CustomerDto customerDto) {
        return jdbcTemplate.update(insertSql, toParam(customerDto), new GeneratedKeyHolder());
    }

    @Override
    public List<Customer> findAllBlack() {
        return null;
    }

    private SqlParameterSource toParam(CustomerDto customerDto) {
        return new MapSqlParameterSource()
                .addValue("customerName", customerDto.getCustomerName())
                .addValue("email", customerDto.getEmail());
    }
}
