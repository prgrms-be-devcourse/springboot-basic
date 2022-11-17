package com.example.springbootbasic.repository.customer;

import com.example.springbootbasic.domain.customer.Customer;
import com.example.springbootbasic.domain.customer.CustomerStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

@Repository
@Profile("dev")
public class JdbcCustomerRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private static final String FIND_ALL_CUSTOMERS_SQL = "SELECT * FROM CUSTOMER";
    private static final String FIND_ALL_CUSTOMERS_BY_STATUS_SQL = "SELECT * FROM CUSTOMER WHERE customer_status = {0}";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        long customerId = resultSet.getLong("customer_id");
        String customerStatus = resultSet.getString("customer_status");
        return new Customer(customerId, CustomerStatus.of(customerStatus));
    };

    @Override
    public List<Customer> findAllCustomers() {
        try {
            return jdbcTemplate.query(FIND_ALL_CUSTOMERS_SQL, customerRowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<Customer> findCustomersByStatus(CustomerStatus status) {
        try {
            return jdbcTemplate.query(MessageFormat.format(FIND_ALL_CUSTOMERS_BY_STATUS_SQL, ":customerStatus"),
                    Collections.singletonMap("customerStatus", status.getType()), customerRowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}
