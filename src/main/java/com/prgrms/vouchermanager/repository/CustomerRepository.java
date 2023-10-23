package com.prgrms.vouchermanager.repository;

import com.prgrms.vouchermanager.domain.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    private final String INSERT_QUERY = "insert into customers(customer_id, name, year_of_birth) values(UUID_TO_BIN(?), ?, ?)";

    public CustomerRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Customer create(Customer customer) {
        jdbcTemplate.update(INSERT_QUERY,
                customer.getId().toString().getBytes(),
                customer.getName(),
                customer.getYearOfBirth());
        return customer;
    }
}
