package org.prgms.vouchermanagement.customer.domain.repository;

import org.prgms.vouchermanagement.customer.domain.entity.Customer;
import org.prgms.vouchermanagement.customer.exception.CustomerException;
import org.prgms.vouchermanagement.global.constant.ExceptionMessageConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CustomerNamedJdbcRepository implements CustomerRepository{

    private static final Logger logger = LoggerFactory.getLogger(CustomerNamedJdbcRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private RowMapper<Customer> customerRowMapper = (resultSet, rowNum) -> {
        String customerName = resultSet.getString("name");
        String email = resultSet.getString("email");
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Customer(customerId, customerName, email, createdAt);
    };

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("createdAt", customer.getCreatedAt());
    }};
    }

    public CustomerNamedJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer insert(Customer customer) {
        int updated = jdbcTemplate.update("INSERT INTO customers(customer_id, name, email, created_at) VALUES(UNHEX(REPLACE(:customerId, '-', '')), :name, :email, :createdAt)",
                toParamMap(customer));
        if (updated != 1) {
            logger.error("Customer insert error");
            throw new CustomerException(ExceptionMessageConstant.EMPTY_CUSTOMER_INSERT);
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        return null;
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Optional<Customer> findByID(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers WHERE customer_id = UNHEX(REPLACE(:customerId, '-', ''))",
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("No result found by ID");
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public void deleteAll() {

    }

    public UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
