package com.programmers.vouchermanagement.customer.repository;

import com.programmers.vouchermanagement.customer.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.programmers.vouchermanagement.customer.repository.util.CustomerDomainMapper.customerRowMapper;
import static com.programmers.vouchermanagement.customer.repository.util.CustomerDomainMapper.customerToParamMap;
import static com.programmers.vouchermanagement.customer.repository.util.CustomerQuery.*;
import static com.programmers.vouchermanagement.util.Constant.UPDATE_ONE_FLAG;
import static com.programmers.vouchermanagement.util.Message.NOT_INSERTED;

@Repository
@Profile("jdbc")
public class CustomerJDBCRepository implements CustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(CustomerJDBCRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerJDBCRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(Customer customer) {
        int update = jdbcTemplate.update(INSERT, customerToParamMap(customer));
        if (update != UPDATE_ONE_FLAG) {
            logger.error(NOT_INSERTED);
            throw new EmptyResultDataAccessException(UPDATE_ONE_FLAG);
        }
    }

    @Override
    public List<Customer> findAllBlackCustomer() {
        return jdbcTemplate.query(FIND_ALL_BLACK_CUSTOMER, customerRowMapper);
    }


    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(FIND_ALL, customerRowMapper);
    }
}
