package org.programmers.springbootbasic.customer.repository;

import org.programmers.springbootbasic.customer.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private static final int SUCCESS = 1;
    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var name = resultSet.getString("name");
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

        return new Customer(customerId, name, createdAt);
    };

    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Override
    public Customer insert(Customer customer) {
        Map<String, ?> parameterMap = Map.of(
                "customerId", customer.getCustomerId().toString().getBytes(),
                "name", customer.getCustomerName(),
                "createdAt", Timestamp.valueOf(customer.getCreatedAt()));

        var insert = jdbcTemplate.update(
                "INSERT INTO customers(customer_id, name, created_at) VALUES (UUID_TO_BIN(:customerId), :name, :createdAt)",
                parameterMap);
        if (insert != SUCCESS) {
            throw new RuntimeException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customers", customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject("SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)",
                            Collections.singletonMap("customerId", customerId.toString().getBytes()),
                            customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("결과 값이 없습니다.", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject("SELECT * FROM customers WHERE name = :name",
                            Collections.singletonMap("name", name),
                            customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("결과 값이 없습니다.", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(UUID customerId) {
        Map<String, ?> parameterMap = Collections.singletonMap("customerId", customerId);
        jdbcTemplate.update("DELETE FROM customers WHERE customer_id = :customerId", parameterMap);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM customers", Collections.emptyMap());
    }
}
