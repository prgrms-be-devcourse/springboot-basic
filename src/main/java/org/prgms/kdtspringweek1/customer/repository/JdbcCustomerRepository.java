package org.prgms.kdtspringweek1.customer.repository;

import org.prgms.kdtspringweek1.customer.entity.Customer;
import org.prgms.kdtspringweek1.exception.JdbcException;
import org.prgms.kdtspringweek1.exception.JdbcExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.*;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String name = resultSet.getString("name");
        boolean isBlackCustomer = resultSet.getBoolean("is_black_customer");
        return Customer.createWithIdAndNameAndIsBlackCustomer(customerId, name, isBlackCustomer);
    };

    private static Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
            put("isBlackCustomer", customer.getIsBlackCustomer());
        }};
    }

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }


    @Override
    public Customer save(Customer customer) {
        int isInserted = jdbcTemplate.update("INSERT INTO customers(customer_id, name, is_black_customer) VALUES (UUID_TO_BIN(:customerId), :name, :isBlackCustomer)",
                toParamMap(customer));
        if (isInserted != 1) {
            logger.error(JdbcExceptionCode.FAIL_TO_INSERT.getMessage());
            throw new JdbcException(JdbcExceptionCode.FAIL_TO_INSERT);
        }

        return customer;
    }

    @Override
    public List<Customer> findAllCustomers() {
        return jdbcTemplate.query("SELECT * FROM customers", customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)",
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("customerId 해당하는 customer를 찾지 못했습니다.");
            return Optional.empty();
        }
    }

    @Override
    public Customer update(Customer customer) {
        int isUpdated = jdbcTemplate.update("UPDATE customers SET name = :name, is_black_customer = :isBlackCustomer WHERE customer_id = UUID_TO_BIN(:customerId)",
                toParamMap(customer));
        if (isUpdated != 1) {
            logger.error(JdbcExceptionCode.FAIL_TO_UPDATE.getMessage());
            throw new JdbcException(JdbcExceptionCode.FAIL_TO_UPDATE);
        }

        return customer;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM customers", Collections.emptyMap());
    }

    @Override
    public void deleteById(UUID customerId) {
        int isUpdated = jdbcTemplate.update("DELETE FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)",
                Collections.singletonMap("customerId", customerId.toString().getBytes()));

        if (isUpdated != 1) {
            logger.error(JdbcExceptionCode.FAIL_TO_DELETE.getMessage());
            throw new JdbcException(JdbcExceptionCode.FAIL_TO_DELETE);
        }
    }

    @Override
    public List<Customer> findAllBlackConsumer() {
        return jdbcTemplate.query("SELECT * FROM customers WHERE is_black_customer = true", customerRowMapper);
    }
}
