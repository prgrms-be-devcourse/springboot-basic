package org.prgrms.kdt.repository;

import org.prgrms.kdt.io.OutputConsole;
import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.util.IntUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.*;

@Repository
public class CustomerJdbcRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);

    private static final RowMapper<Customer> customerRowMapper = (resultSet, rowNum) -> {
        var name = resultSet.getString("name");
        var email = resultSet.getString("email");
        var customerId = IntUtils.toUUID(resultSet.getBytes("customer_id"));
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        return new Customer(customerId, name, email, lastLoginAt, createdAt);
    };

    public CustomerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Customer> insertCustomer(Customer customer) throws DuplicateKeyException {
        var paramMap = new HashMap<String, Object>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("createdAt", customer.getCreateAt());
            put("lastLoginAt", customer.getLastLoginAt());
        }};
        try {
            int insertInt = jdbcTemplate.update("INSERT INTO customers(customer_id, name, email, created_at, last_login_at) VALUES (UUID_TO_BIN(:customerId), :name, :email, :createdAt, :lastLoginAt)", paramMap);
            if (insertInt > 0) {
                return Optional.of(customer);
            }
            return Optional.empty();
        } catch (Exception e) {
            logger.info("email : {} {}", customer.getEmail(), e.getMessage());
            OutputConsole.printMessage(MessageFormat.format("{0} is already exist customer", customer.getEmail()));
            return Optional.empty();
        }
    }

    public List<Customer> findAllCustomer() {
        return jdbcTemplate.query("SELECT * FROM customers", customerRowMapper);
    }

    public Optional<Customer> findCustomerById(UUID customerId) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)",
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),
                    customerRowMapper));
        } catch (Exception e) {
            OutputConsole.printMessage("WRONG : invalid input");
            return Optional.empty();
        }
    }

    public void deleteAllCustomer() {
        jdbcTemplate.update("DELETE FROM customers", Collections.emptyMap());
    }
}
