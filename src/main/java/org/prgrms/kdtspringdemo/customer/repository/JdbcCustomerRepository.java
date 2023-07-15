package org.prgrms.kdtspringdemo.customer.repository;

import org.prgrms.kdtspringdemo.customer.constant.CustomerQuery;
import org.prgrms.kdtspringdemo.customer.model.entity.Customer;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static org.prgrms.kdtspringdemo.customer.exception.CustomerExceptionMessage.*;
import static org.prgrms.kdtspringdemo.util.JdbcUtils.*;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {
    private static final String CUSTOMER_ID = "customer_id";
    private static final String NICKNAME = "nickname";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Customer> customerRowMapper = (resultSet, i)
            -> new Customer(toUUID(resultSet.getBytes(CUSTOMER_ID)), resultSet.getString(NICKNAME));

    private Map<String, Object> toParamMap(Customer customer) {
        return Map.of(
                CUSTOMER_ID, customer.getCustomerId().toString().getBytes(),
                NICKNAME, customer.getNickname()
        );
    }

    @Override
    public Customer save(Customer customer) {
        int savedCustomer;
        try {
            savedCustomer = jdbcTemplate.update(CustomerQuery.CREATE.getQuery(), toParamMap(customer));
            if (savedCustomer != SUCCESS_SAVE_QUERY) {
                throw new RuntimeException(FAILED_CUSTOMER_SAVE_QUERY.getMessage());
            }
        } catch (DuplicateKeyException e) {
            throw new DuplicateKeyException(DUPLICATE_NICKNAME.getMessage());
        }

        return customer;
    }

    @Override
    public Customer findById(UUID customerId) {
        Customer customer;
        try {
            customer = jdbcTemplate.queryForObject(CustomerQuery.FIND_ID.getQuery(),
                    Collections.singletonMap(CUSTOMER_ID, customerId.toString().getBytes()),
                    customerRowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException(CUSTOMER_ID_LOOKUP_FAILED.getMessage());
        }

        return customer;
    }

    @Override
    public Customer findByNickname(String nickname) {
        Customer customer;
        try {
            customer = jdbcTemplate.queryForObject(CustomerQuery.FIND_NICKNAME.getQuery(),
                    Collections.singletonMap(NICKNAME, nickname),
                    customerRowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException(CUSTOMER_NICKNAME_LOOKUP_FAILED.getMessage());
        }

        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(CustomerQuery.FIND_ALL.getQuery(), customerRowMapper);
    }

    @Override
    public Customer update(Customer customer) {
        return null;
    }

    @Override
    public Customer deleteById(UUID customerId) {
        return null;
    }
}
