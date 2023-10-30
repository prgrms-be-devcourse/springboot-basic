package org.prgms.springbootbasic.repository.customer;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.common.UtilMethod;
import org.prgms.springbootbasic.domain.customer.Customer;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
@Slf4j
@Profile({"dev", "prod"})
public class CustomerDatabaseRepository implements CustomerRepository{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerDatabaseRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer save(Customer customer) {
        Optional<Customer> foundCustomer = findById(customer.getCustomerId());

        if (foundCustomer.isPresent()){
            jdbcTemplate.update("UPDATE customers SET name = :name WHERE customer_id = UNHEX(REPLACE(:customerId, '-', ''))",
                    toParamMap(customer));
        } else {
            jdbcTemplate.update("INSERT INTO customers(customer_id, name, email, is_blacked) VALUES (UNHEX(REPLACE(:customerId, '-', '')), :name, :email, :isBlacked)",
                    toParamMap(customer));
        }

        return customer;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject("SELECT * FROM customers WHERE customer_id = UNHEX(REPLACE(:customerId, '-', ''))",
                            Collections.singletonMap("customerId", customerId.toString().getBytes()),
                            mapToCustomer));
        } catch (EmptyResultDataAccessException e) {
            log.info("customerId에 해당하는 customer가 DB에 없음.");
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject("SELECT * FROM customers WHERE email = :email",
                            Collections.singletonMap("email", email),
                            mapToCustomer));
        } catch (EmptyResultDataAccessException e) {
            log.info("email에 해당하는 customer가 DB에 없음.");
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customers", mapToCustomer);
    }

    @Override
    public List<Customer> findBlackAll() {
        return jdbcTemplate.query("SELECT * FROM customers WHERE is_blacked = true", mapToCustomer);
    }

    @Override
    public Optional<Customer> deleteById(UUID customerId) {
        Optional<Customer> deleteCustomer = findById(customerId);

        jdbcTemplate.update("DELETE FROM customers WHERE customer_id = UNHEX(REPLACE(:customerId, '-', ''))",
                Collections.singletonMap("customerId", customerId.toString().getBytes()));
        return deleteCustomer;
    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE FROM customers", Collections.emptyMap());
    }

    private static Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>(){{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("isBlacked", customer.isBlacked());
        }};
    }

    private static RowMapper<Customer> mapToCustomer = (rs, rowNum) -> {
        String customerName = rs.getString("name");
        UUID customerId = UtilMethod.bytesToUUID(rs.getBytes("customer_id"));
        String email = rs.getString("email");
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime lastLoginAt = rs.getTimestamp("last_login_at") != null
                ? rs.getTimestamp("last_login_at").toLocalDateTime() : null;
        boolean isBlacked = rs.getBoolean("is_blacked");

        return new Customer(customerId, customerName, email, lastLoginAt, createdAt, isBlacked);
    };
}
