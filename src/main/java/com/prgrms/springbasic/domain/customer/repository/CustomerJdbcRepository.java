package com.prgrms.springbasic.domain.customer.repository;

import com.prgrms.springbasic.domain.customer.entity.Customer;
import com.prgrms.springbasic.util.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {

    private static final String INSERT = "INSERT INTO customers(customer_id, name, email) VALUES(UUID_TO_BIN(?), ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM customers";
    private static final String FIND_BY_ID = "SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(?)";
    private static final String FIND_BY_EMAIL = "SELECT * FROM customers WHERE email = ?";
    private static final String FIND_BLACKLISTS = "SELECT * FROM customers WHERE is_blackList = 1";

    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);

    private final JdbcTemplate jdbcTemplate;
    public CustomerJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Customer> customerRowMapper = (resultSet, rowNum) -> {
        UUID customerId = UUIDUtils.toUUID(resultSet.getBytes("customer_id"));
        String customerName = resultSet.getString("name");
        String email = resultSet.getString("email");
        return new Customer(customerId, customerName, email);
    };

    @Override
    public Customer save(Customer customer) {
        jdbcTemplate.update(INSERT,
                customer.getCustomerId().toString().getBytes(),
                customer.getName(),
                customer.getEmail());
        return customer;
    }

    @Override
    public List<Customer> findAllBlackList() {
        return jdbcTemplate.query(FIND_BLACKLISTS, customerRowMapper);
    }

    @Override
    public Optional<Customer> findCustomerById(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID,
                    customerRowMapper, customerId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            logger.info("Customer not found");
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findCustomerByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_EMAIL,
                    customerRowMapper, email));
        } catch (EmptyResultDataAccessException e) {
            logger.info("Customer not found");
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(SELECT_ALL, customerRowMapper);
    }
}
