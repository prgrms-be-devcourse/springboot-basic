package com.programmers.springbasic.domain.customer.repository;

import com.programmers.springbasic.domain.customer.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CustomerJdbcRepository implements CustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);

    private static final String CUSTOMER_INSERT_QUERY = "INSERT INTO customers(customer_id, name, email) VALUES (UUID_TO_BIN(?), ?, ?)";
    private static final String CUSTOMER_SELECT_BY_ID_QUERY = "SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(?)";
    private static final String CUSTOMER_SELECT_ALL_QUERY = "SELECT * FROM customers";
    private static final String CUSTOMER_UPDATE_QUERY = "UPDATE customers SET name = ?, email = ? WHERE customer_id = UUID_TO_BIN(?)";
    private static final String CUSTOMER_DELETE_QUERY = "DELETE FROM customers WHERE customer_id = UUID_TO_BIN(?)";

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");

        return new Customer(customerId, name, email);
    };

    static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(Customer customer) {
        try {
            jdbcTemplate.update(CUSTOMER_INSERT_QUERY,
                    customer.getCustomerId().toString().getBytes(),
                    customer.getName(),
                    customer.getEmail());
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(CUSTOMER_SELECT_BY_ID_QUERY,
                    customerRowMapper,
                    customerId.toString().getBytes()));
        } catch (DataAccessException e) {
            logger.error(e.getMessage());

            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(CUSTOMER_SELECT_ALL_QUERY, customerRowMapper);
    }

    @Override
    public void update(Customer customer) {
        try {
            jdbcTemplate.update(CUSTOMER_UPDATE_QUERY,
                    customer.getName(),
                    customer.getEmail(),
                    customer.getCustomerId().toString().getBytes());
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void delete(UUID customerId) {
        try {
            jdbcTemplate.update(CUSTOMER_DELETE_QUERY,
                    customerId.toString().getBytes());
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
    }
}
