package com.programmers.springbootbasic.domain.customer.repository;

import com.programmers.springbootbasic.common.utils.UUIDConverter;
import com.programmers.springbootbasic.domain.customer.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CustomerJDBCRepository implements CustomerRepository {
    private static final String INSERT_QUERY = "INSERT INTO customers(customer_id, email, name) VALUES(UUID_TO_BIN(?), ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM customers";
    private static final String SELECT_BLACKLIST_QUERY = "SELECT * FROM customers WHERE isBlacklist = TRUE";
    private static final String SELECT_BY_EMAIL_QUERY = "SELECT * FROM customers WHERE email = ?";
    private static final String UPDATE_QUERY = "UPDATE customers SET email = ?, name = ?, isBlacklist = ? WHERE customer_id = UUID_TO_BIN(?)";
    private static final String TRUNCATE_TABLE = "DELETE FROM customers";

    private static final RowMapper<Customer> ROW_MAPPER = (resultSet, rowNum) -> {
        UUID customerId = UUIDConverter.toUUID(resultSet.getBytes("customer_id"));
        String email = resultSet.getString("email");
        String name = resultSet.getString("name");
        boolean isBlacklist = resultSet.getBoolean("isBlacklist");
        return Customer.builder()
                .customerId(customerId)
                .email(email)
                .name(name)
                .isBlacklist(isBlacklist)
                .build();
    };

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_EMAIL_QUERY, ROW_MAPPER, email));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Customer save(Customer customer) {
        jdbcTemplate.update(INSERT_QUERY, customer.getCustomerId().toString().getBytes(),
                customer.getEmail(),
                customer.getName());
        return customer;
    }

    @Override
    public int update(Customer customer) {
        return jdbcTemplate.update(UPDATE_QUERY, customer.getEmail(),
                customer.getName(),
                customer.isBlacklist(),
                customer.getCustomerId().toString().getBytes());
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(SELECT_ALL_QUERY, ROW_MAPPER);
    }

    @Override
    public List<Customer> findBlacklist() {
        return jdbcTemplate.query(SELECT_BLACKLIST_QUERY, ROW_MAPPER);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(TRUNCATE_TABLE);
    }


}
