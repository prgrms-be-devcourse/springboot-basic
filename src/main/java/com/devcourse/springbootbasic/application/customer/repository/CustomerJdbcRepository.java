package com.devcourse.springbootbasic.application.customer.repository;

import com.devcourse.springbootbasic.application.customer.model.Customer;
import com.devcourse.springbootbasic.application.global.exception.ErrorMessage;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.global.utils.Utils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {

    private static final RowMapper<Customer> customerRowMapper = (resultSet, rowNum) -> {
        UUID customerId = Utils.toUUID(resultSet.getBytes("customer_id"));
        String name = resultSet.getString("name");
        boolean isBlack = resultSet.getBoolean("black");
        return new Customer(customerId, name, isBlack);
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer insert(Customer customer) {
        try {
            var updateResult = jdbcTemplate.update(
                    "INSERT INTO customers(customer_id, name, black) VALUES (UUID_TO_BIN(:customerId), :name, :black)",
                    toParamMap(customer)
            );
            if (updateResult != 1) {
                throw new InvalidDataException(ErrorMessage.INVALID_CREATION.getMessageText());
            }
            return customer;
        } catch (DataAccessException exception) {
            throw new InvalidDataException(ErrorMessage.INVALID_SQL.getMessageText(), exception.getCause());
        }
    }

    @Override
    public Customer update(Customer customer) {
        try {
            var updateResult = jdbcTemplate.update(
                    "UPDATE customers SET name = :name, black = :black WHERE customer_id = UUID_TO_BIN(:customerId)",
                    toParamMap(customer)
            );
            if (updateResult != 1) {
                throw new InvalidDataException(ErrorMessage.INVALID_UPDATE.getMessageText());
            }
            return customer;
        } catch (DataAccessException exception) {
            throw new InvalidDataException(ErrorMessage.INVALID_SQL.getMessageText(), exception.getCause());
        }
    }

    @Override
    public List<Customer> findAll() {
        try {
            return jdbcTemplate.query(
                    "SELECT customer_id, name, black FROM customers",
                    customerRowMapper
            );
        } catch (DataAccessException exception) {
            throw new InvalidDataException(ErrorMessage.INVALID_SQL.getMessageText(), exception.getCause());
        }
    }

    @Override
    public List<Customer> findAllBlackCustomers() {
        try {
            return jdbcTemplate.query(
                    "SELECT customer_id, name, black FROM customers WHERE black = TRUE",
                    customerRowMapper
            );
        } catch (DataAccessException e) {
            throw new InvalidDataException(ErrorMessage.INVALID_SQL.getMessageText(), e.getCause());
        }
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "SELECT customer_id, name, black FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)",
                            Collections.singletonMap("customerId", customerId.toString().getBytes()),
                            customerRowMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "SELECT customer_id, name, black FROM customers WHERE name = :name",
                            Collections.singletonMap("name", name),
                            customerRowMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        try {
            jdbcTemplate.update(
                    "DELETE FROM customers",
                    Collections.emptyMap()
            );
        } catch (DataAccessException exception) {
            throw new InvalidDataException(ErrorMessage.INVALID_SQL.getMessageText(), exception.getCause());
        }
    }

    @Override
    public void deleteById(UUID customerId) {
        try {
            jdbcTemplate.update(
                    "DELETE FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)",
                    Map.of("customerId", customerId.toString().getBytes())
            );
        } catch (DataAccessException e) {
            throw new InvalidDataException(ErrorMessage.INVALID_SQL.getMessageText(), e.getCause());
        }
    }

    private Map<String, Object> toParamMap(Customer customer) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("customerId", customer.getCustomerId().toString().getBytes());
        paramMap.put("name", customer.getName());
        paramMap.put("black", customer.isBlack());
        return paramMap;
    }

}
