package com.tangerine.voucher_system.application.customer.repository;

import com.tangerine.voucher_system.application.customer.model.Customer;
import com.tangerine.voucher_system.application.customer.model.Name;
import com.tangerine.voucher_system.application.global.exception.ErrorMessage;
import com.tangerine.voucher_system.application.global.exception.InvalidDataException;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile({"default", "test"})
public class JdbcCustomerRepository implements CustomerRepository {

    private static final RowMapper<Customer> customerRowMapper = (resultSet, rowNum) -> {
        UUID customerId = UUID.fromString(resultSet.getString("customer_id"));
        Name name = new Name(resultSet.getString("name"));
        boolean isBlack = resultSet.getBoolean("black");
        return new Customer(customerId, name, isBlack);
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer insert(Customer customer) {
        try {
            int updateResult = jdbcTemplate.update(
                    "INSERT INTO customers(customer_id, name, black) VALUES (:customerId, :name, :black)",
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
            int updateResult = jdbcTemplate.update(
                    "UPDATE customers SET name = :name, black = :black WHERE customer_id = :customerId",
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
            return List.of();
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
            return List.of();
        }
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "SELECT customer_id, name, black FROM customers WHERE customer_id = :customerId",
                            Collections.singletonMap("customerId", customerId.toString()),
                            customerRowMapper
                    )
            );
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(Name name) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "SELECT customer_id, name, black FROM customers WHERE name = :name",
                            Collections.singletonMap("name", name.getValue()),
                            customerRowMapper
                    )
            );
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(UUID customerId) {
        try {
            jdbcTemplate.update(
                    "DELETE FROM customers WHERE customer_id = :customerId",
                    Map.of("customerId", customerId.toString())
            );
        } catch (DataAccessException e) {
            throw new InvalidDataException(ErrorMessage.INVALID_SQL.getMessageText(), e.getCause());
        }
    }

    private Map<String, Object> toParamMap(Customer customer) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("customerId", customer.customerId().toString());
        paramMap.put("name", customer.name().getValue());
        paramMap.put("black", customer.isBlack());
        return paramMap;
    }

}
