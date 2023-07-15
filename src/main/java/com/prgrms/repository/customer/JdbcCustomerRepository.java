package com.prgrms.repository.customer;

import com.prgrms.exception.NotUpdateException;
import com.prgrms.model.customer.Customer;
import com.prgrms.presentation.message.ErrorMessage;
import com.prgrms.repository.DataRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final String COLUMNS = "last_login_at, customer_id, name, email, created_at";
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final DataRowMapper dataRowMapper;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate, DataRowMapper dataRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataRowMapper = dataRowMapper;
    }

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("customer_id", customer.getCustomerId());
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("created_at", Timestamp.valueOf(customer.getCreatedAt()));
            put("last_login_at", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
        }};
    }

    @Override
    public Customer insert(Customer customer) {
        int update = jdbcTemplate.update("INSERT INTO customers(customer_id, name, email, created_at) VALUES (:customer_id, :name, :email, :created_at)",
                toParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("Noting was inserted");
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        int update = jdbcTemplate.update("UPDATE customers SET name = :name, email = :email, last_login_at = :last_login_at WHERE customer_id = :customer_id",
                toParamMap(customer));
        if (update != 1) {
            throw new NotUpdateException(ErrorMessage.NOT_UPDATE.getMessage());
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select " + COLUMNS + " from customers", dataRowMapper.getCustomerRowMapper());
    }

    public Optional<Customer> findById(int customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select " + COLUMNS + " from customers WHERE customer_id = :customer_id",
                    Collections.singletonMap("customer_id", customerId),
                    dataRowMapper.getCustomerRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            logger.debug(ErrorMessage.NO_RESULT_RETURN_EMPTY.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select " + COLUMNS + " from customers WHERE name = :name",
                    Collections.singletonMap("name", name),
                    dataRowMapper.getCustomerRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            logger.debug(ErrorMessage.NO_RESULT_RETURN_EMPTY.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select " + COLUMNS + " from customers WHERE email = :email",
                    Collections.singletonMap("email", email),
                    dataRowMapper.getCustomerRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            logger.info(ErrorMessage.NO_RESULT_RETURN_EMPTY.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public boolean existsById(int voucher_id) {

        return jdbcTemplate.queryForObject("SELECT 1 EXISTS(SELECT FROM vouchers WHERE voucher_id = :voucher_id)"
                , Collections.singletonMap("voucher_id"
                        , voucher_id), Boolean.class);
    }

    private void deleteAll() {
        jdbcTemplate.update("DELETE FROM customers", Collections.emptyMap());
    }

}
