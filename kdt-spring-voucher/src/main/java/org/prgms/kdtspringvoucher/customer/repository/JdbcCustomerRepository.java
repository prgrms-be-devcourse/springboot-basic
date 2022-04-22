package org.prgms.kdtspringvoucher.customer.repository;

import org.prgms.kdtspringvoucher.customer.domain.Customer;
import org.prgms.kdtspringvoucher.customer.domain.CustomerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Transactional(readOnly = true)
public class JdbcCustomerRepository implements CustomerRepository{

    private final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        int save = jdbcTemplate.update(
                "insert into customers(customer_id, name,email,customer_type,created_at) values (UUID_TO_BIN(:customerId),:name,:email,:customerType,:createdAt)",
                toCustomerParamMap(customer)
        );
        if (save != 1) {
            throw new RuntimeException("Nothing was saved");
        }
        return findById(customer.getCustomerId()).orElse(null);
    }

    @Override
    public Customer update(Customer customer) {
        int update = jdbcTemplate.update(
                "update customers set name = :name, email = :email, customer_type = :customerType, last_login_at = :lastLoginAt where UUID_TO_BIN(:customerId)",
                toCustomerParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return findById(customer.getCustomerId()).orElse(null);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "select * from customers where customer_id = UUID_TO_BIN(:customerId)",
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),
                    getCustomerRowMapper()
            ));
        } catch (EmptyResultDataAccessException exception) {
            logger.error("Got empty result", exception);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "select * from customers where email = :email",
                    Collections.singletonMap("email", email),
                    getCustomerRowMapper()
            ));
        } catch (EmptyResultDataAccessException exception) {
            logger.error("Got empty result", exception);
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findByName(String name) {
        return jdbcTemplate.query(
                "select * from customers where name = :name",
                Collections.singletonMap("name", name),
                getCustomerRowMapper());
    }

    @Override
    public List<Customer> findBlackList() {
        return jdbcTemplate.query("select * from customers where customer_type = 'BLACKLIST'",getCustomerRowMapper());
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customers", getCustomerRowMapper());
    }

    @Override
    @Transactional
    public void deleteAll() {
        jdbcTemplate.update("delete from customers", Collections.emptyMap());
    }

    private Map<String, Object> toCustomerParamMap(Customer customer) {
        Map<String, Object> customerParamMap = new HashMap<>();
        customerParamMap.put("customerId", customer.getCustomerId().toString().getBytes());
        customerParamMap.put("name", customer.getName());
        customerParamMap.put("email", customer.getEmail());
        customerParamMap.put("customerType", customer.getCustomerType().toString());
        customerParamMap.put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
        customerParamMap.put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
        return customerParamMap;
    }

    private RowMapper<Customer> getCustomerRowMapper() {
        return (resultSet, i) -> {
            UUID customerId = toUUID(resultSet.getBytes("customer_id"));
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            CustomerType customerType = CustomerType.valueOf(resultSet.getString("customer_type"));
            LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ? resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
            LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
            return new Customer(customerId, name, email, customerType, lastLoginAt, createdAt);
        };
    }

    private UUID toUUID(byte[] customerIds) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(customerIds);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
