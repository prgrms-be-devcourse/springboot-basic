package com.programmers.vouchermanagement.customer.repository;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.domain.CustomerType;
import com.programmers.vouchermanagement.global.utils.UuidUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("default")
public class JdbcCustomerRepository implements CustomerRepository {

    private static final String READ_BLACK = "SELECT * FROM customer WHERE customer_type = 'BLACK'";
    private static final String READ_ONCE = "SELECT * FROM customer WHERE customer_id = UUID_TO_BIN(?)";

    private static final RowMapper<Customer> customerRowMapper = (resultSet, index) -> {

        UUID customerId = UuidUtil.bytesToUUID(resultSet.getBytes("customer_id"));
        String name = resultSet.getString("name");
        CustomerType customerType = CustomerType.valueOf(resultSet.getString("customer_type"));

        return new Customer(customerId, name, customerType);
    };

    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> findAllBlack() {
        return jdbcTemplate.query(READ_BLACK, customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {

        try {
            return Optional.of(jdbcTemplate.queryForObject(READ_ONCE, customerRowMapper, customerId.toString()));

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
