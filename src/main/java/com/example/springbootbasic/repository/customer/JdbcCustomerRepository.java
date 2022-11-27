package com.example.springbootbasic.repository.customer;

import com.example.springbootbasic.domain.customer.Customer;
import com.example.springbootbasic.domain.customer.CustomerStatus;
import com.example.springbootbasic.domain.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

import static com.example.springbootbasic.exception.customer.JdbcCustomerRepositoryExceptionMessage.*;
import static com.example.springbootbasic.repository.customer.CustomerParam.*;
import static com.example.springbootbasic.repository.customer.JdbcCustomerSql.*;
import static com.example.springbootbasic.repository.voucher.VoucherParam.*;

@Repository
public class JdbcCustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private SqlParameterSource toParamSource(Customer customer, Voucher voucher) {
        return new MapSqlParameterSource()
                .addValue(CUSTOMER_ID.getParam(),  customer.getCustomerId())
                .addValue(CUSTOMER_STATUS.getParam(), customer.getStatus().getType())
                .addValue(VOUCHER_ID.getParam(), voucher.getVoucherId())
                .addValue(VOUCHER_TYPE.getParam(), voucher.getVoucherType().getVoucherType())
                .addValue(VOUCHER_DISCOUNT_VALUE.getParam(), voucher.getDiscountValue());
    }

    private SqlParameterSource toParamSource(Customer customer) {
        return new MapSqlParameterSource()
                .addValue(CUSTOMER_ID.getParam(), customer.getCustomerId())
                .addValue(CUSTOMER_STATUS.getParam(), customer.getStatus().getType());
    }

    private RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        long customerId = resultSet.getLong(CUSTOMER_ID.getColumn());
        String customerStatus = resultSet.getString(CUSTOMER_STATUS.getColumn());
        return new Customer(customerId, CustomerStatus.of(customerStatus));
    };

    private RowMapper<Long> customerVoucherRowMapper = (resultSet, i) -> {
        return resultSet.getLong(VOUCHER_ID.getColumn());
    };

    public List<Customer> findAllCustomers() {
        try {
            return jdbcTemplate.query(FIND_ALL_CUSTOMERS.getSql(), customerRowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Customer> findCustomersByStatus(CustomerStatus status) {
        try {
            validateCustomerStatusNull(status);
            return jdbcTemplate.query(FIND_ALL_CUSTOMERS_BY_STATUS.getSql(),
                    Collections.singletonMap(CUSTOMER_STATUS.getParam(), status.getType()), customerRowMapper);
        } catch (EmptyResultDataAccessException | IllegalArgumentException e) {
            logger.error("Fail - {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Long> findCustomerIdsByVoucherId(Long voucherId) {
        try {
            return jdbcTemplate.query(FIND_CUSTOMERS_ID_BY_VOUCHER_ID.getSql(),
                    Collections.singletonMap(VOUCHER_ID.getParam(), voucherId), (resultSet, i) -> resultSet.getLong(CUSTOMER_ID.getColumn()));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public Customer saveCustomer(Customer customer) {
        GeneratedKeyHolder customerIdHolder = new GeneratedKeyHolder();
        try {
            validateCustomerNull(customer);
            jdbcTemplate.update(INSERT_CUSTOMER.getSql(), toParamSource(customer), customerIdHolder);
        } catch (DataAccessException | IllegalArgumentException e) {
            logger.error("Fail - {}", e.getMessage());
        }
        return new Customer(customerIdHolder.getKey().longValue(), customer.getStatus());
    }

    private void validateCustomerNull(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException(CUSTOMER_NULL_EXCEPTION.getMessage());
        }
    }

    public Customer saveVoucher(Customer customer, Voucher voucher) {
        try {
            validateVoucherNull(voucher);
            customer.receiveFrom(voucher);
            jdbcTemplate.update(INSERT_CUSTOMER_VOUCHER.getSql(), toParamSource(customer, voucher));
        } catch (DataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
        }
        return customer;
    }

    public Customer findCustomerById(Long customerId) {
        try {
            return jdbcTemplate.queryForObject(FIND_CUSTOMER_BY_ID.getSql(),
                    Collections.singletonMap(CUSTOMER_ID.getParam(), customerId), customerRowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
        }
        return new Customer(customerId);
    }

    public List<Long> findVoucherIdsByCustomerId(long customerId) {
        try {
            return jdbcTemplate.query(FIND_CUSTOMER_ALL_VOUCHERS_ID.getSql(),
                    Collections.singletonMap(CUSTOMER_ID.getParam(), customerId), customerVoucherRowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public void deleteAllCustomers() {
        try {
            jdbcTemplate.update(DELETE_ALL_CUSTOMERS.getSql(), Collections.emptyMap());
        } catch (DataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
        }
    }

    public void deleteAllCustomerVoucher() {
        try {
            jdbcTemplate.update(DELETE_ALL_CUSTOMER_VOUCHER.getSql(), Collections.emptyMap());
        } catch (DataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
        }
    }

    public void deleteAllVouchersByCustomerId(long customerId) {
        try {
            jdbcTemplate.update(DELETE_CUSTOMER_ALL_VOUCHERS.getSql(),
                    Collections.singletonMap(CUSTOMER_ID.getParam(), customerId));
        } catch (DataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
        }
    }

    private void validateVoucherNull(Voucher voucher) {
        if (voucher == null) {
            throw new IllegalArgumentException(CUSTOMER_VOUCHER_NULL_EXCEPTION.getMessage());
        }
    }

    private void validateCustomerStatusNull(CustomerStatus status) {
        if (status == null) {
            throw new IllegalArgumentException(CUSTOMER_STATUS_NULL_EXCEPTION.getMessage());
        }
    }


}
