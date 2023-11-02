package com.programmers.vouchermanagement.customer.repository;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.util.DomainMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.programmers.vouchermanagement.constant.Constant.UPDATE_ONE_FLAG;
import static com.programmers.vouchermanagement.constant.Message.NOT_INSERTED;
import static com.programmers.vouchermanagement.customer.repository.CustomerQuery.FIND_ALL_BLACK_CUSTOMER;
import static com.programmers.vouchermanagement.customer.repository.CustomerQuery.INSERT;

@Repository
@Profile("jdbc")
public class CustomerJDBCRepository implements CustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(CustomerJDBCRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final DomainMapper domainMapper;

    public CustomerJDBCRepository(NamedParameterJdbcTemplate jdbcTemplate, DomainMapper domainMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.domainMapper = domainMapper;
    }

    @Override
    public List<Customer> findAllBlackCustomer() {
        return jdbcTemplate.query(FIND_ALL_BLACK_CUSTOMER, domainMapper.customerRowMapper);
    }

    public void save(Customer customer) {
        int update = jdbcTemplate.update(INSERT, domainMapper.customerToParamMap(customer));
        if (update != UPDATE_ONE_FLAG) {
            logger.error(NOT_INSERTED);
            throw new RuntimeException(NOT_INSERTED);
        }
    }
}
