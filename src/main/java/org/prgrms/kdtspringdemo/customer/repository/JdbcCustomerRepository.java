package org.prgrms.kdtspringdemo.customer.repository;

import org.prgrms.kdtspringdemo.customer.constant.CustomerQuery;
import org.prgrms.kdtspringdemo.customer.exception.CustomerIdNotFoundException;
import org.prgrms.kdtspringdemo.customer.exception.CustomerNicknameNotFoundException;
import org.prgrms.kdtspringdemo.customer.exception.CustomerSaveFailedException;
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
                logger.error("원인 : {} -> 에러 메시지 : {}", customer.getCustomerId(), CUSTOMER_ID_LOOKUP_FAILED);
                throw new CustomerSaveFailedException(FAILED_CUSTOMER_SAVE_QUERY);
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
            logger.error("원인 : {} -> 에러 메시지 : {}", customerId, CUSTOMER_ID_LOOKUP_FAILED);
            throw new CustomerIdNotFoundException(CUSTOMER_ID_LOOKUP_FAILED);
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
            logger.error("원인 : {} -> 에러 메시지 : {}", nickname, DUPLICATE_NICKNAME);
            throw new CustomerNicknameNotFoundException(CUSTOMER_NICKNAME_LOOKUP_FAILED);
        }

        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(CustomerQuery.FIND_ALL.getQuery(), customerRowMapper);
    }

    @Override
    public Customer update(Customer customer) {
        findById(customer.getCustomerId());
        jdbcTemplate.update(CustomerQuery.UPDATE.getQuery(), toParamMap(customer));

        return customer;
    }

    @Override
    public void deleteById(UUID customerId) {
        jdbcTemplate.update(CustomerQuery.DELETE.getQuery(),
                Collections.singletonMap(CUSTOMER_ID, customerId.toString().getBytes()));
    }
}
