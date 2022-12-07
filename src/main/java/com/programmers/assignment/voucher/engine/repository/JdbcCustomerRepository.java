package com.programmers.assignment.voucher.engine.repository;

import com.programmers.assignment.voucher.engine.model.Customer;
import com.programmers.assignment.voucher.util.dto.CustomerDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Customer> customerRowMapperWithId = (resultSet, i) -> {
        var customerId = resultSet.getLong("customer_id");
        var customerName = resultSet.getString("name");
        var customerEmail = resultSet.getString("email");
        var customerUuid = toUUID(resultSet.getBytes("customer_uuid"));
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        var lastLogin = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        return new Customer(customerId, customerUuid, customerName, customerEmail, createdAt, lastLogin);
    };

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
//        var customerId = resultSet.getLong("customer_id");
        var customerName = resultSet.getString("name");
        var customerEmail = resultSet.getString("email");
        var customerUuid = toUUID(resultSet.getBytes("customer_uuid"));
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
//        var lastLogin = resultSet.getTimestamp("last_login_at") != null ?
//                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        return new Customer(customerUuid, customerName, customerEmail, createdAt);
    };

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId());
            put("customerUuid", customer.getCustomerUuid().toString().getBytes());
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("createdAt", customer.getCreatedAt());
            put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
        }};
    }

    private Map<String, Object> dtoToParamMap(CustomerDto customerDto, Long customerId) {
        return new HashMap<>() {{
            put("customerId", customerId);
            put("name", customerDto.name());
            put("email", customerDto.email());
        }};
    }

    @Override
    public Customer save(Customer customer) {
        var update = jdbcTemplate.update("insert into customers(customer_uuid, name, email, created_at) values (UUID_TO_BIN(:customerUuid), :name, :email, :createdAt)",
                toParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public Customer update(CustomerDto customerDto, Long customerId) {
        var update = jdbcTemplate.update("update customers set name = :name, email = :email where customer_id = :customerId",
                dtoToParamMap(customerDto, customerId)
        );
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return findById(customerId).get();
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customers", customerRowMapperWithId);
    }

    @Override
    public Optional<Customer> findById(Long customerId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "select * from customers where customer_id = :customerId",
                            Collections.singletonMap("customerId", customerId),
                            customerRowMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByUuid(UUID customerUuid) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "select * from customers where customer_uuid = UUID_TO_BIN(:customerUuid)",
                            Collections.singletonMap("customerUuid", customerUuid.toString().getBytes()),
                            customerRowMapperWithId
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
                            "select * from customers where name = :name",
                            Collections.singletonMap("name", name),
                            customerRowMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "select * from customers where email = :email",
                            Collections.singletonMap("email", email),
                            customerRowMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from customers", Collections.EMPTY_MAP);
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject("select count(*) from customers", Collections.EMPTY_MAP, Integer.class);
    }
}