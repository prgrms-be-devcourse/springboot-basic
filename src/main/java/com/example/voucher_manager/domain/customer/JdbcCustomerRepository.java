package com.example.voucher_manager.domain.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.*;

@Repository
public class JdbcCustomerRepository implements CustomerRepository{
    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var name = resultSet.getString("name");
        var email = resultSet.getString("email");
        return new Customer(customerId, name, email);
    };

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{ // 순서 상관없이 key로 mapping이됨
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
            put("email", customer.getEmail());
        }};
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customers", customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers where customer_id = UUID_TO_BIN(:customerId)",
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e){
            logger.error("Got Empty Result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findCustomerHasVoucher(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select c.customer_id, c.name, c.email from customers as c join vouchers as v ON c.customer_id = v.owner_id where v.voucher_id = UUID_TO_BIN(:voucherId)",
                    Map.of("voucherId", voucherId.toString().getBytes()),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e){
            logger.error("Got Empty Result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers where email = :email",
                    Collections.singletonMap("email", email),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e){
            logger.error("Got Empty Result", e);
            return Optional.empty();
        }
    }

    @Override
    public Customer update(Customer customer) {
        var update = jdbcTemplate.update("UPDATE customers SET name = :name WHERE customer_id = UUID_TO_BIN(:customerId)",
                toParamMap(customer));
        if (update != 1){
            logger.error("Noting was updated");
            return customer;
        }
        logger.info("Customer data Successfully updated.");
        return customer;
    }

    @Override
    public Optional<Customer> insert(Customer customer) {
        try {
            var insert = jdbcTemplate.update(
                    "INSERT INTO customers(customer_id, name, email) VALUES (UUID_TO_BIN(:customerId), :name, :email)",
                    toParamMap(customer)
            );
            if (insert != 1) {
                logger.error("Noting was inserted");
                return Optional.empty();
            }
        } catch (DuplicateKeyException e) {
            // logger.error("Duplicate entry can't inserted");
            throw new DuplicateKeyException("Duplicate entry can't inserted");
        }
        logger.info("Customer data Successfully inserted.");
        return Optional.of(customer);
    }

    @Override
    public void clear() {
        jdbcTemplate.update("DELETE FROM customers", Map.of());
    }

    @Override
    public boolean deleteCustomerById(UUID customerId) {
        var delete = jdbcTemplate.update("DELETE FROM customers where customer_id = UUID_TO_BIN(:customerId)",
                Collections.singletonMap("customerId", customerId));
        if (delete != 1){
            logger.error("Noting was deleted.");
            return false;
        }
        logger.info("Customer data Successfully deleted.");
        return true;
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
