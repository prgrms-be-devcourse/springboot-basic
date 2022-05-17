package org.devcourse.voucher.customer.repository;

import org.devcourse.voucher.customer.model.Customer;
import org.devcourse.voucher.customer.model.Email;
import org.devcourse.voucher.error.DataInsertFailException;
import org.devcourse.voucher.error.DataUpdateFailException;
import org.devcourse.voucher.utils.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID customerId = JdbcUtils.toUUID(resultSet.getBytes("customer_id"));
        String name = resultSet.getString("name");
        Email email = new Email(resultSet.getString("email"));
        return new Customer(customerId, name, email);
    };

    private static Map<String, Object> toParamMap(Customer customer) {
        return Map.of(
                "customerId", customer.getCustomerId().toString().getBytes(),
                "name", customer.getName(),
                "email", customer.getEmail().getAddress()
        );
    }

    @Override
    public Customer insert(Customer customer) {
        logger.info("Repository : Record a voucher insert");
        int inserted = jdbcTemplate.update("INSERT INTO customers(customer_id, name, email) VALUES(UUID_TO_BIN(:customerId), :name, :email)",
                toParamMap(customer));
        if (inserted != 1) {
            throw new DataInsertFailException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        logger.info("Repository : Record a voucher read");
        return jdbcTemplate.query("select * from customers", customerRowMapper);
    }

    @Override
    public Customer update(Customer customer) {
        logger.info("Repository : Record a voucher update");
        int updated = jdbcTemplate.update("UPDATE customers SET name = :name, email = :email " +
                "WHERE customer_id = UUID_TO_BIN(:customerId)", toParamMap(customer));
        if (updated != 1) {
            throw new DataUpdateFailException("Nothing was updated");
        }
        return customer;
    }

    @Override
    public void deleteAll() {
        logger.info("Repository : Record a voucher delete");
        jdbcTemplate.update("DELETE FROM customers", Collections.emptyMap());
    }
}
