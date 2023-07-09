package org.promgrammers.springbootbasic.domain.customer.repository.impl;

import org.promgrammers.springbootbasic.domain.customer.model.Customer;
import org.promgrammers.springbootbasic.domain.customer.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final NamedParameterJdbcTemplate template;

    private static final String SAVE = "INSERT INTO customers(customer_id, username, customer_type) VALUES(:customerId, :username, :customerType)";
    private static final String FIND_BY_ID = "SELECT * FROM customers WHERE customer_id = :customerId";
    private static final String FIND_BY_USERNAME = "SELECT * FROM customers WHERE username = :username";
    private static final String FIND_BY_VOUCHER_ID = "SELECT c.* FROM customers c INNER JOIN vouchers v ON c.customer_id = v.customer_id WHERE v.voucher_id = :voucherId";
    private static final String FIND_ALL = "SELECT * FROM customers";
    private static final String UPDATE = "UPDATE customers SET username = :username, customer_type = :customerType WHERE customer_id = :customerId";
    private static final String DELETE_ALL = "DELETE FROM customers";
    private static final String DELETE_BY_ID = "DELETE FROM customers WHERE customer_id = :customerId";

    public JdbcCustomerRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    private SqlParameterSource createParameterSource(Customer customer) {
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("customerId", customer.getCustomerId().toString())
                .addValue("username", customer.getUsername())
                .addValue("customerType", customer.getCustomerType().toString());

        return paramSource;
    }

    @Override
    public Customer save(Customer customer) {
        SqlParameterSource parameterSource = createParameterSource(customer);
        int saveCount = template.update(SAVE, parameterSource);

        if (saveCount != 1) {
            throw new DataAccessException("고객 저장에 실패 했습니다. => " + customer.getCustomerId()) {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };
        }
        return customer;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            Map<String, Object> param = Map.of("customerId", customerId);
            Customer customer = template.queryForObject(FIND_BY_ID, param, customerRowMapper);
            return Optional.ofNullable(customer);
        } catch (EmptyResultDataAccessException e) {
            logger.error("해당 ID를 찾을 수 없습니다. => " + customerId);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByUsername(String username) {
        try {
            Map<String, Object> param = Map.of("username", username);
            Customer customer = template.queryForObject(FIND_BY_USERNAME, param, customerRowMapper);
            return Optional.ofNullable(customer);
        } catch (EmptyResultDataAccessException e) {
            logger.error("해당 Username을 찾을 수 없습니다. => " + username);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByVoucherId(UUID voucherId) {
        try {
            Map<String, Object> param = Map.of("voucherId", voucherId);
            Customer customer = template.queryForObject(FIND_BY_VOUCHER_ID, param, customerRowMapper);
            return Optional.ofNullable(customer);
        } catch (EmptyResultDataAccessException e) {
            logger.error("해당 바우처를 가진 고객을 찾을 수 없습니다. => voucherId: " + voucherId);
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findAll() {
        return template.query(FIND_ALL, customerRowMapper);
    }

    @Override
    public Customer update(Customer customer) {
        template.update(UPDATE, createParameterSource(customer));
        return customer;
    }

    @Override
    public void deleteAll() {
        template.update(DELETE_ALL, Collections.emptyMap());
    }

    @Override
    public void deleteById(UUID customerId) {
        Map<String, Object> param = Map.of("customerId", customerId);
        template.update(DELETE_BY_ID, param);
    }

    private final RowMapper<Customer> customerRowMapper = ((rs, rowNum) -> {
        String customerId = rs.getString("customer_id");
        String username = rs.getString("username");

        return new Customer(UUID.fromString(customerId), username);
    });
}