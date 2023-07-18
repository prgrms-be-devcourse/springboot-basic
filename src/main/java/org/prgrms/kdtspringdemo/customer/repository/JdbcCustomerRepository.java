package org.prgrms.kdtspringdemo.customer.repository;

import org.prgrms.kdtspringdemo.customer.exception.CustomerIdNotFoundException;
import org.prgrms.kdtspringdemo.customer.exception.CustomerNicknameNotFoundException;
import org.prgrms.kdtspringdemo.customer.model.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private static final String CUSTOMER_ID = "customer_id";
    private static final String NICKNAME = "nickname";
    private static final String SAVE_QUERY = "INSERT INTO customer(customer_id, nickname) VALUES(UUID_TO_BIN(:customer_id), :nickname)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM customer WHERE customer_id = UUID_TO_BIN(:customer_id)";
    private static final String FIND_BY_NICKNAME_QUERY = "SELECT * FROM customer WHERE nickname = :nickname";
    private static final String FIND_ALL_QUERY = "SELECT * FROM customer";
    private static final String UPDATE_QUERY = "UPDATE customer SET nickname = :nickname WHERE customer_id = UUID_TO_BIN(:customer_id)";
    private static final String DELETE_QUERY = "DELETE FROM customer WHERE customer_id = UUID_TO_BIN(:customer_id)";
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
        try {
            jdbcTemplate.update(SAVE_QUERY, toParamMap(customer));
        } catch (DuplicateKeyException e) {
            throw new DuplicateKeyException(DUPLICATE_NICKNAME.getMessage());
        }

        return customer;
    }

    @Override
    public Customer findById(UUID customerId) {
        Customer customer;
        try {
            customer = jdbcTemplate.queryForObject(FIND_BY_ID_QUERY,
                    Collections.singletonMap(CUSTOMER_ID, customerId.toString().getBytes()),
                    customerRowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("원인 : {} -> 에러 메시지 : {}", customerId, CUSTOMER_ID_LOOKUP_FAILED);
            throw new CustomerIdNotFoundException(CUSTOMER_ID_LOOKUP_FAILED);
        }

        return customer;
    }

    @Override
    public Customer findByNickname(String nickname) {
        Customer customer;
        try {
            customer = jdbcTemplate.queryForObject(FIND_BY_NICKNAME_QUERY,
                    Collections.singletonMap(NICKNAME, nickname),
                    customerRowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("원인 : {} -> 에러 메시지 : {}", nickname, DUPLICATE_NICKNAME);
            throw new CustomerNicknameNotFoundException(CUSTOMER_NICKNAME_LOOKUP_FAILED);
        }

        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, customerRowMapper);
    }

    @Override
    public Customer update(Customer customer) {
        findById(customer.getCustomerId());
        jdbcTemplate.update(UPDATE_QUERY, toParamMap(customer));

        return customer;
    }

    @Override
    public void deleteById(UUID customerId) {
        jdbcTemplate.update(DELETE_QUERY,
                Collections.singletonMap(CUSTOMER_ID, customerId.toString().getBytes()));
    }
}
