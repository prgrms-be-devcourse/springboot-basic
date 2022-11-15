package org.prgrms.springorder.domain.customer.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.springorder.domain.customer.model.Customer;
import org.prgrms.springorder.domain.customer.model.CustomerStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);

    private static final String findByIdSql = "SELECT * FROM customers WHERE customer_id = :customerId";

    private static final String insertSql = "INSERT INTO customers(customer_id, name, email, last_login_at) "
        + "VALUES (:customerId, :name, :email, :lastLoginAt)";

    private static final String selectAllSql = "SELECT * FROM customers";

    private static final String deleteAllSql = "DELETE FROM customers";

    private static final String updateByIdSql
        = "UPDATE customers set name = :name, email = :email, customer_status = :customerStatus, last_login_at = :lastLoginAt";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<Customer> customerRowMapper = (rs, rowNum) -> {

        String customerId = rs.getString("customer_id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        CustomerStatus customerStatus = CustomerStatus.of(rs.getString("customer_status"));

        LocalDateTime createdAt = rs.getTimestamp("created_at")
            != null ? rs.getTimestamp("created_at").toLocalDateTime() : null;

        LocalDateTime lastLoginAt = rs.getTimestamp("last_login_at")
            == null ? null : rs.getTimestamp("last_login_at").toLocalDateTime();

        return new Customer(UUID.fromString(customerId), name, email, lastLoginAt, createdAt,
            customerStatus);
    };

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("customerStatus", customer.getCustomerStatus().name());

            put("createdAt", customer.getCreatedAt() == null ? null
                : Timestamp.valueOf(customer.getCreatedAt()));
            put("lastLoginAt", customer.getLastLoginAt() == null ? null : Timestamp.valueOf(
                customer.getLastLoginAt()));
        }};
    }

    public CustomerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {

        try {
            Customer findCustomer = jdbcTemplate.queryForObject(findByIdSql,
                Collections.singletonMap("customerId", customerId)
                , customerRowMapper);

            return Optional.ofNullable(findCustomer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Customer insert(Customer customer) {
        try {
            jdbcTemplate.update(insertSql, toParamMap(customer));

            return customer;
        } catch (DataAccessException e) {
            logger.error("voucher insert error. name {},  message {}", e.getClass().getName(),
                e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(selectAllSql, customerRowMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(deleteAllSql, Collections.emptyMap());
    }

    @Override
    public Customer update(Customer customer) {
        jdbcTemplate.update(updateByIdSql, toParamMap(customer));
        return customer;
    }
}
