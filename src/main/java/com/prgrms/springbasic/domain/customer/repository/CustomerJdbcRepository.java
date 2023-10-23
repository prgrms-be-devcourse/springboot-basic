package com.prgrms.springbasic.domain.customer.repository;

import com.prgrms.springbasic.domain.customer.entity.Customer;
import com.prgrms.springbasic.util.UUIDUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {

    private static final String INSERT_QUERY = "INSERT INTO customers(customer_id, name, email) VALUES(UUID_TO_BIN(?), ?, ?)";

    private final JdbcTemplate jdbcTemplate;
    public CustomerJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Customer> customerRowMapper = (resultSet, rowNum) -> {
        UUID customerId = UUIDUtils.toUUID(resultSet.getBytes("customer_id"));
        String customerName = resultSet.getString("name");
        String email = resultSet.getString("email");
        return new Customer(customerId, customerName, email);
    };

    @Override
    public Customer save(Customer customer) {
        jdbcTemplate.update(INSERT_QUERY,
                customer.getCustomerId().toString().getBytes(),
                customer.getName(),
                customer.getEmail());
        return customer;
    }

    @Override
    public List<Customer> findAllBlackList() {
        return null;
    }
}
